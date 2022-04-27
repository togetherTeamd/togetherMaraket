package com.project.together.controller;

import com.project.together.entity.Address;
import com.project.together.entity.User;
import com.project.together.service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MapPositionController {

    private final MapService mapService;

    @GetMapping("/mapChange")
    public String map(){
        return "map/mapChange";
    }

    @RequestMapping(value="/mapSave", method = RequestMethod.POST)
    public String insertPosition(HttpServletRequest request,
                                 @SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser
            , Model model) throws Exception {
        String lan = request.getParameter("x");
        String lon = request.getParameter("y");

        System.out.println(lan + lon);


        return "redirect:/";
    }

    @GetMapping("/mapFind")
    public String mapFind(HttpServletRequest request,
                          @SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser
            , Model model){

        /* 사용자들 정보 가져오기 */
        User seller = new User();
        seller.setAddress(new Address());
        Address address = seller.getAddress();
        address.setLat("37.570028");
        address.setLon("126.989072");

        Address buyer = loginUser.getAddress();
        buyer.setLat("37.57081522");
        buyer.setLon("127.00160213");

        model.addAttribute("seller", address);
        model.addAttribute("buyer", buyer);

        return "map/mapFind";
    }
}
