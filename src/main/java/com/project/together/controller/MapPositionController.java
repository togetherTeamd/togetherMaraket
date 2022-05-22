package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.Address;
import com.project.together.entity.Item;
import com.project.together.entity.User;
import com.project.together.service.ItemService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MapPositionController {

    private final UserService userService;

    private final ItemService itemService;

    @GetMapping("/mapChange")
    public String map(){
        return "map/mapChange";
    }

    @GetMapping("mapChange_str")
    public String mapStr(HttpServletRequest request,
                         @AuthenticationPrincipal PrincipalDetails user
            , Model model) {
        User loginUser = userService.findById(user.getUsername());
        Address address = loginUser.getAddress();

        String myaddr = address.getCity() + " " + address.getStreet();

        model.addAttribute("user", myaddr);

        return "map/mapChange_str";
    }

    @RequestMapping(value="/mapSave", method = RequestMethod.POST)
    public String insertPosition(HttpServletRequest request, @AuthenticationPrincipal PrincipalDetails user
            , Model model) throws Exception {
        User loginUser = userService.findById(user.getUsername());
        Address address = loginUser.getAddress();

        String lat = request.getParameter("lat");
        String lon = request.getParameter("lon");

        address.setLat(lat);
        address.setLon(lon);

        userService.update(loginUser);

        return "redirect:/";
    }

    @GetMapping("/mapFind")
    public String mapFind(HttpServletRequest request, @AuthenticationPrincipal PrincipalDetails user
            , Model model){

        User loginUser = userService.findById(user.getUsername());

        String itemId = request.getParameter("itemId");

        Item item = itemService.findOne(Long.parseLong(itemId));
        /* 사용자들 정보 가져오기 */

        Address address = item.getAddress();

        Address buyer = loginUser.getAddress();

        model.addAttribute("seller", address);
        model.addAttribute("buyer", buyer);

        return "map/mapFind";
    }
}
