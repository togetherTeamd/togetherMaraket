var map, marker_s, marker_e;
var drawInfoArr = [];
var resultdrawArr = [];

function initMap() {
    // 지도 생성
    map = new Tmapv2.Map("map_div", {
        center: new Tmapv2.LatLng(37.570028, 126.986072),
        width: "100%",
        height: "400px",
        zoom: 15,
        zoomControl: true,
        scrollwheel: true
    });

    // 마커 초기화
    marker_s = new Tmapv2.Marker(
        {
            icon: "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_a.png",
            iconSize: new Tmapv2.Size(24, 38),
            map: map
        });

    marker_e = new Tmapv2.Marker(
        {
            icon: "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_a.png",
            iconSize: new Tmapv2.Size(24, 38),
            map: map
        });
}

function selectMapPoint() {
    initMap();

    var lon, lat;

    $("#btn_position")
        .click(
            function () {
                // API 사용요청
                var city_do = $("#sido").val();
                var gu_gun = $("#gugun").val();
                var dong = $("#dong").val();
                var bunji = $('#bunji').val();

                $.ajax({
                    method: "GET",
                    url: "https://apis.openapi.sk.com/tmap/geo/geocoding?version=1&format=json&callback=result",
                    async: false,
                    data: {
                        "appKey": "l7xx48396a5595344e91870ba48981274ebf",
                        "coordType": "WGS84GEO",
                        "city_do": city_do,
                        "gu_gun": gu_gun,
                        "dong": dong,
                        "bunji": bunji
                    },
                    success: function (response) {
                        var resultData = response.coordinateInfo;

                        if (resultData.lon.length > 0) {
                            lon = resultData.lon;
                            lat = resultData.lat;
                        } else {
                            lon = resultData.newLon;
                            lat = resultData.newLat;
                        }

                        //기존 마커 삭제
                        marker_s.setMap(null);

                        var markerPosition = new Tmapv2.LatLng(
                            Number(lat), Number(lon));
                        //마커 올리기
                        marker_s = new Tmapv2.Marker(
                            {
                                position: markerPosition,
                                icon: "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_a.png",
                                iconSize: new Tmapv2.Size(
                                    24, 38),
                                map: map
                            });
                        map.setCenter(markerPosition);

                    },
                    error: function (request, status, error) {
                        console.log("code:"
                            + request.status + "\n"
                            + "message:"
                            + request.responseText
                            + "\n" + "error:" + error);
                    }
                });

            });

    $("#btn_save")
        .click(
            function () {
                $.ajax({
                    type: "POST",
                    url: "/mapSave",
                    data: { "id" : $('#id').val(),
                        "x" : lat, "y" : lon},
                    success: function() {
                        alert("저장 성공");
                        location.reload();
                    }, error: function(){
                        alert('저장 실패');
                    }
                })
            });
}

function findMapDirection(x1, y1, x2, y2) {
    initMap();

    // 시작
    marker_s = new Tmapv2.Marker(
        {
            position : new Tmapv2.LatLng(parseFloat(y1), parseFloat(x1)),
            icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png",
            iconSize : new Tmapv2.Size(24, 38),
            map : map
        });
    // 도착
    marker_e = new Tmapv2.Marker(
        {
            position: new Tmapv2.LatLng(parseFloat(y2), parseFloat(x2)),
            icon: "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png",
            iconSize: new Tmapv2.Size(24, 38),
            map: map
        });

    $
        .ajax({
            method: "POST",
            url: "https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&format=json&callback=result",
            async: false,
            data: {
                "appKey": "l7xx48396a5595344e91870ba48981274ebf",
                "startX": x1,
                "startY": y1,
                "endX": x2,
                "endY": y2,
                "reqCoordType": "WGS84GEO",
                "resCoordType": "EPSG3857",
                "startName": "출발지",
                "endName": "도착지"
            },
            success: function (response) {
                var resultData = response.features;

                //결과 출력
                var tDistance = "총 거리 : "
                    + ((resultData[0].properties.totalDistance) / 1000)
                        .toFixed(1) + "km,";
                var tTime = " 총 시간 : "
                    + ((resultData[0].properties.totalTime) / 60)
                        .toFixed(0) + "분";

                $("#result1").text(tDistance + tTime);

                //기존 그려진 라인 & 마커가 있다면 초기화
                if (resultdrawArr.length > 0) {
                    for (var i in resultdrawArr) {
                        resultdrawArr[i]
                            .setMap(null);
                    }
                    resultdrawArr = [];
                }

                drawInfoArr = [];

                for (var i in resultData) { //for문 [S]
                    var geometry = resultData[i].geometry;
                    var properties = resultData[i].properties;


                    if (geometry.type == "LineString") {
                        for (var j in geometry.coordinates) {
                            // 경로들의 결과값(구간)들을 포인트 객체로 변환
                            var latlng = new Tmapv2.Point(
                                geometry.coordinates[j][0],
                                geometry.coordinates[j][1]);
                            // 포인트 객체를 받아 좌표값으로 변환
                            var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                latlng);
                            // 포인트객체의 정보로 좌표값 변환 객체로 저장
                            var convertChange = new Tmapv2.LatLng(
                                convertPoint._lat,
                                convertPoint._lng);
                            // 배열에 담기
                            drawInfoArr.push(convertChange);
                        }
                    } else {
                        var markerImg = "";
                        var pType = "";
                        var size;

                        if (properties.pointType == "S") { //출발지 마커
                            markerImg = "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png";
                            pType = "S";
                            size = new Tmapv2.Size(24, 38);
                        } else if (properties.pointType == "E") { //도착지 마커
                            markerImg = "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png";
                            pType = "E";
                            size = new Tmapv2.Size(24, 38);
                        } else { //각 포인트 마커
                            markerImg = "http://topopen.tmap.co.kr/imgs/point.png";
                            pType = "P";
                            size = new Tmapv2.Size(8, 8);
                        }

                        // 경로들의 결과값들을 포인트 객체로 변환
                        var latlon = new Tmapv2.Point(
                            geometry.coordinates[0],
                            geometry.coordinates[1]);

                        // 포인트 객체를 받아 좌표값으로 다시 변환
                        var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                            latlon);

                        var routeInfoObj = {
                            markerImage: markerImg,
                            lng: convertPoint._lng,
                            lat: convertPoint._lat,
                            pointType: pType
                        };

                        // Marker 추가
                        marker_p = new Tmapv2.Marker(
                            {
                                position: new Tmapv2.LatLng(
                                    routeInfoObj.lat,
                                    routeInfoObj.lng),
                                icon: routeInfoObj.markerImage,
                                iconSize: size,
                                map: map
                            });
                    }
                }//for문 [E]
                drawLine(drawInfoArr);
            },
            error: function (request, status, error) {
                console.log("code:" + request.status + "\n"
                    + "message:" + request.responseText + "\n"
                    + "error:" + error);
            }
        });
}

function drawLine(arrPoint) {
    var polyline_;

    polyline_ = new Tmapv2.Polyline({
        path: arrPoint,
        strokeColor: "#DD0000",
        strokeWeight: 6,
        map: map
    });
    resultdrawArr.push(polyline_);
}

function findMapStraight(x1,y1,x2,y2) {
    $
        .ajax({
            method : "GET",
            url : "https://apis.openapi.sk.com/tmap/routes/distance?version=1&format=json&callback=result",//
            async : false,
            data : {
                "appKey" : "l7xx48396a5595344e91870ba48981274ebf",
                "startX" : x1,
                "startY" : y1,
                "endX" : x2,
                "endY" : y2,
                "reqCoordType" : "WGS84GEO"
            },
            success : function(response) {
                var distance = response.distanceInfo.distance;

                $("#result2").text("두점의 직선거리 : " + distance + "m");
            },
            error : function(request, status, error) {
                console.log("code:" + request.status + "\n"
                    + "message:" + request.responseText + "\n"
                    + "error:" + error);
            }
        });

}

function findAddress(){
    initMap();

    function reverseGeo(lon, lat) {
        $
            .ajax({
                method: "GET",
                url: "https://apis.openapi.sk.com/tmap/geo/reversegeocoding?version=1&format=json&callback=result",
                async: false,
                data: {
                    "appKey": "l7xx48396a5595344e91870ba48981274ebf",
                    "coordType": "WGS84GEO",
                    "addressType": "A10",
                    "lon": lon,
                    "lat": lat
                },
                success: function (response) {
                    // json에서 주소 파싱
                    var arrResult = response.addressInfo;

                    var lastLegal = arrResult.legalDong
                        .charAt(arrResult.legalDong.length - 1);

                    // 새주소
                    newRoadAddr = arrResult.city_do + ' '
                        + arrResult.gu_gun + ' ';

                    if (arrResult.eup_myun == ''
                        && (lastLegal == "읍" || lastLegal == "면")) {//읍면
                        newRoadAddr += arrResult.legalDong;
                    } else {
                        newRoadAddr += arrResult.eup_myun;
                    }
                    newRoadAddr += ' ' + arrResult.roadName + ' '
                        + arrResult.buildingIndex;

                    // 새주소 건물명 체크
                    if (arrResult.legalDong != ''
                        && (lastLegal != "읍" && lastLegal != "면")) {//법정동과 읍면이 같은 경우

                        if (arrResult.buildingName != '') {//빌딩명 존재하는 경우
                            newRoadAddr += (' (' + arrResult.legalDong
                                + ', ' + arrResult.buildingName + ') ');
                        } else {
                            newRoadAddr += (' (' + arrResult.legalDong + ')');
                        }
                    } else if (arrResult.buildingName != '') {//빌딩명만 존재하는 경우
                        newRoadAddr += (' (' + arrResult.buildingName + ') ');
                    }

                    // 구주소
                    jibunAddr = arrResult.city_do + ' '
                        + arrResult.gu_gun + ' '
                        + arrResult.legalDong + ' ' + arrResult.ri
                        + ' ' + arrResult.bunji;
                    //구주소 빌딩명 존재
                    if (arrResult.buildingName != '') {//빌딩명만 존재하는 경우
                        jibunAddr += (' ' + arrResult.buildingName);
                    }

                    result = "새주소 : " + newRoadAddr + "</br>";
                    result += "지번주소 : " + jibunAddr + "</br>";
                    result += "위경도좌표 : " + lat + ", " + lon;

                    var resultDiv = document.getElementById("result");
                    resultDiv.innerHTML = result;

                },
                error: function (request, status, error) {
                    console.log("code:" + request.status + "\n"
                        + "message:" + request.responseText + "\n"
                        + "error:" + error);
                }
            });
    }
}

/* 문자열 주소로 찾기 (추후 이걸로 변경 예정)
function findStringMap(addr){
    initMap();

    $.ajax({
        method : "GET",
        url : "https://apis.openapi.sk.com/tmap/geo/fullAddrGeo?version=1&format=json&callback=result",
        async : false,
        data : {
            "appKey" : "l7xx48396a5595344e91870ba48981274ebf",
            "coordType" : "WGS84GEO",
            "fullAddr" : addr
        },
        success : function(response) {

            var resultInfo = response.coordinateInfo; // .coordinate[0];
            console.log(resultInfo);

            // 기존 마커 삭제
            marker1.setMap(null);

            // 검색 결과 정보가 없을 때 처리
            if (resultInfo.coordinate.length == 0) {
                $("#result").text(
                    "요청 데이터가 올바르지 않습니다.");
            } else {
                var lon, lat;
                var resultCoordinate = resultInfo.coordinate[0];
                if (resultCoordinate.lon.length > 0) {
                    // 구주소
                    lon = resultCoordinate.lon;
                    lat = resultCoordinate.lat;
                } else {
                    // 신주소
                    lon = resultCoordinate.newLon;
                    lat = resultCoordinate.newLat
                }

                var lonEntr, latEntr;

                if (resultCoordinate.lonEntr == undefined && resultCoordinate.newLonEntr == undefined) {
                    lonEntr = 0;
                    latEntr = 0;
                } else {
                    if (resultCoordinate.lonEntr.length > 0) {
                        lonEntr = resultCoordinate.lonEntr;
                        latEntr = resultCoordinate.latEntr;
                    } else {
                        lonEntr = resultCoordinate.newLonEntr;
                        latEntr = resultCoordinate.newLatEntr;
                    }
                }

                var markerPosition = new Tmapv2.LatLng(Number(lat),Number(lon));

                // 마커 올리기
                marker1 = new Tmapv2.Marker(
                    {
                        position : markerPosition,
                        icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_a.png",
                        iconSize : new Tmapv2.Size(
                            24, 38),
                        map : map
                    });
                map.setCenter(markerPosition);

                // 검색 결과 표출
                var matchFlag, newMatchFlag;
                // 검색 결과 주소를 담을 변수
                var address = '', newAddress = '';
                var city, gu_gun, eup_myun, legalDong, adminDong, ri, bunji;
                var buildingName, buildingDong, newRoadName, newBuildingIndex, newBuildingName, newBuildingDong;

                // 새주소일 때 검색 결과 표출
                // 새주소인 경우 matchFlag가 아닌
                // newMatchFlag가 응답값으로
                // 온다
                if (resultCoordinate.newMatchFlag.length > 0) {
                    // 새(도로명) 주소 좌표 매칭
                    // 구분 코드
                    newMatchFlag = resultCoordinate.newMatchFlag;

                    // 시/도 명칭
                    if (resultCoordinate.city_do.length > 0) {
                        city = resultCoordinate.city_do;
                        newAddress += city + "\n";
                    }

                    // 군/구 명칭
                    if (resultCoordinate.gu_gun.length > 0) {
                        gu_gun = resultCoordinate.gu_gun;
                        newAddress += gu_gun + "\n";
                    }

                    // 읍면동 명칭
                    if (resultCoordinate.eup_myun.length > 0) {
                        eup_myun = resultCoordinate.eup_myun;
                        newAddress += eup_myun + "\n";
                    } else {
                        // 출력 좌표에 해당하는
                        // 법정동 명칭
                        if (resultCoordinate.legalDong.length > 0) {
                            legalDong = resultCoordinate.legalDong;
                            newAddress += legalDong + "\n";
                        }
                        // 출력 좌표에 해당하는
                        // 행정동 명칭
                        if (resultCoordinate.adminDong.length > 0) {
                            adminDong = resultCoordinate.adminDong;
                            newAddress += adminDong + "\n";
                        }
                    }
                    // 출력 좌표에 해당하는 리 명칭
                    if (resultCoordinate.ri.length > 0) {
                        ri = resultCoordinate.ri;
                        newAddress += ri + "\n";
                    }
                    // 출력 좌표에 해당하는 지번 명칭
                    if (resultCoordinate.bunji.length > 0) {
                        bunji = resultCoordinate.bunji;
                        newAddress += bunji + "\n";
                    }
                    // 새(도로명)주소 매칭을 한
                    // 경우, 길 이름을 반환
                    if (resultCoordinate.newRoadName.length > 0) {
                        newRoadName = resultCoordinate.newRoadName;
                        newAddress += newRoadName + "\n";
                    }
                    // 새(도로명)주소 매칭을 한
                    // 경우, 건물 번호를 반환
                    if (resultCoordinate.newBuildingIndex.length > 0) {
                        newBuildingIndex = resultCoordinate.newBuildingIndex;
                        newAddress += newBuildingIndex + "\n";
                    }
                    // 새(도로명)주소 매칭을 한
                    // 경우, 건물 이름를 반환
                    if (resultCoordinate.newBuildingName.length > 0) {
                        newBuildingName = resultCoordinate.newBuildingName;
                        newAddress += newBuildingName + "\n";
                    }
                    // 새주소 건물을 매칭한 경우
                    // 새주소 건물 동을 반환
                    if (resultCoordinate.newBuildingDong.length > 0) {
                        newBuildingDong = resultCoordinate.newBuildingDong;
                        newAddress += newBuildingDong + "\n";
                    }
                    // 검색 결과 표출
                    if (lonEntr > 0) {
                        var docs = "<a style='color:orange' href='#webservice/docs/fullTextGeocoding'>Docs</a>"
                        var text = "검색결과(새주소) : " + newAddress + ",\n 응답코드:" + newMatchFlag + "(상세 코드 내역은 " + docs + " 에서 확인)" + "</br> 위경도좌표(중심점) : " + lat + ", " + lon + "</br>위경도좌표(입구점) : " + latEntr + ", " + lonEntr;
                        $("#result").html(text);
                    } else {
                        var docs = "<a style='color:orange' href='#webservice/docs/fullTextGeocoding'>Docs</a>"
                        var text = "검색결과(새주소) : " + newAddress + ",\n 응답코드:" + newMatchFlag + "(상세 코드 내역은 " + docs + " 에서 확인)" + "</br> 위경도좌표(입구점) : 위경도좌표(입구점)이 없습니다.";
                        $("#result").html(text);
                    }
                }

                // 구주소일 때 검색 결과 표출
                // 구주소인 경우 newMatchFlag가
                // 아닌 MatchFlag가 응닶값으로
                // 온다
                if (resultCoordinate.matchFlag.length > 0) {
                    // 매칭 구분 코드
                    matchFlag = resultCoordinate.matchFlag;

                    // 시/도 명칭
                    if (resultCoordinate.city_do.length > 0) {
                        city = resultCoordinate.city_do;
                        address += city + "\n";
                    }
                    // 군/구 명칭
                    if (resultCoordinate.gu_gun.length > 0) {
                        gu_gun = resultCoordinate.gu_gun;
                        address += gu_gun+ "\n";
                    }
                    // 읍면동 명칭
                    if (resultCoordinate.eup_myun.length > 0) {
                        eup_myun = resultCoordinate.eup_myun;
                        address += eup_myun + "\n";
                    }
                    // 출력 좌표에 해당하는 법정동
                    // 명칭
                    if (resultCoordinate.legalDong.length > 0) {
                        legalDong = resultCoordinate.legalDong;
                        address += legalDong + "\n";
                    }
                    // 출력 좌표에 해당하는 행정동
                    // 명칭
                    if (resultCoordinate.adminDong.length > 0) {
                        adminDong = resultCoordinate.adminDong;
                        address += adminDong + "\n";
                    }
                    // 출력 좌표에 해당하는 리 명칭
                    if (resultCoordinate.ri.length > 0) {
                        ri = resultCoordinate.ri;
                        address += ri + "\n";
                    }
                    // 출력 좌표에 해당하는 지번 명칭
                    if (resultCoordinate.bunji.length > 0) {
                        bunji = resultCoordinate.bunji;
                        address += bunji + "\n";
                    }
                    // 출력 좌표에 해당하는 건물 이름
                    // 명칭
                    if (resultCoordinate.buildingName.length > 0) {
                        buildingName = resultCoordinate.buildingName;
                        address += buildingName + "\n";
                    }
                    // 출력 좌표에 해당하는 건물 동을
                    // 명칭
                    if (resultCoordinate.buildingDong.length > 0) {
                        buildingDong = resultCoordinate.buildingDong;
                        address += buildingDong + "\n";
                    }
                    // 검색 결과 표출
                    if (lonEntr > 0) {
                        var docs = "<a style='color:orange' href='#webservice/docs/fullTextGeocoding'>Docs</a>";
                        var text = "검색결과(지번주소) : "+ address+ ","+ "\n"+ "응답코드:"+ matchFlag+ "(상세 코드 내역은 "+ docs+ " 에서 확인)"+ "</br>"+ "위경도좌표(중심점) : "+ lat+ ", "+ lon+ "</br>"+ "위경도좌표(입구점) : "+ latEntr+ ", "+ lonEntr;
                        $("#result").html(text);
                    } else {
                        var docs = "<a style='color:orange' href='#webservice/docs/fullTextGeocoding'>Docs</a>";
                        var text = "검색결과(지번주소) : "+ address+ ","+ "\n"+ "응답코드:"+ matchFlag+ "(상세 코드 내역은 "+ docs+ " 에서 확인)"+ "</br>"+ "위경도좌표(입구점) : 위경도좌표(입구점)이 없습니다.";
                        $("#result").html(text);
                    }
                }
            }
        },
        error : function(request, status, error) {
            console.log(request);
            console.log("code:"+request.status + "\n message:" + request.responseText +"\n error:" + error);
            // 에러가 발생하면 맵을 초기화함
            // markerStartLayer.clearMarkers();
            // 마커초기화
            map.setCenter(new Tmapv2.LatLng(37.570028, 126.986072));
            $("#result").html("");

        }
    });
}
*/
function sendRequest(){
    $('#send').click(function(){
        $.ajax({
            type: "POST",
            url: "/",
            data: {
                "" : $('#').val(),
                "" : $('#').val(),
            },
            success: function(){

            }
        })
    })
}