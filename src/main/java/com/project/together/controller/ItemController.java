package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.*;
import com.project.together.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final SellService sellService;
    private final CategoryService categoryService;
    private final LoginService loginService;
    private final FileService filesService;
    private final UserService userService;

    @GetMapping("items/new")
    public String createForm(Model model, @AuthenticationPrincipal PrincipalDetails loginUser) {

        User user = userService.findById(loginUser.getUsername());

        if(categoryService.findAll().isEmpty()) {
            categoryService.createCategory();
        }
        //User user = loginService.login(loginUser.getUserId(), loginUser.getUserPw());
        model.addAttribute("user", user);
        model.addAttribute("form", new ItemForm());
        model.addAttribute("categories", categoryService.findAll());
        return "items/createItemForm";
    }//상품생성

    @PostMapping("items/new")//
    public String create(ItemForm form, @RequestParam("categoryId") Long categoryId,
                         @AuthenticationPrincipal PrincipalDetails user,  BindingResult result,
                         @RequestPart List<MultipartFile> files) throws Exception {

        User loginUser = userService.findById(user.getUsername());

        System.out.println("상품정보 잘 들어왔나 : " + form.toString());
        System.out.println("카테고리 아이디는 왔는가 : " + categoryId);
        System.out.println("유저정보 왔는가 : " + loginUser.getUserId());

        if(loginUser == null) {
            result.reject("sellFail", "로그인 후 이용해 주세요");
            return "redirect:/";
        }

        Item item = new Item();
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setContents(form.getContents());
        item.setItemLevel(form.getItemLevel());
        item.setDealForm(form.getDealForm());
        item.setEnul(form.getEnul());
        item.setSeller(loginUser.getUserId());
        item.setCategoryId(categoryId);
        if(loginUser.getMannerScore() > 0) {
            item.setMannerItem(1L);
        }
        else {
            item.setMannerItem(0L);
        }//아이템 판매자가 매너인인지 확인
        item.setCreatedAt(LocalDateTime.now());
        //아이템 생성 세션으로부터 로그인정보, 폼으로부터 아이템 속성값들 받아옴

        for(MultipartFile mf : files){
            Files file = new Files();
            String sourceFileName = mf.getOriginalFilename(); //파일 원본 이름 ex)증명사진.jpg
            String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();//확장자 .앞부분 반환
            FilenameUtils.removeExtension(sourceFileName);//확장자 .뒷부분 반환

            File destinationFile;
            String destinationFileName;
            String fileUrl = "C:\\Users\\chlgu\\Desktop\\togetherMaraket\\src\\main\\resources\\static\\img\\"; // img 저장할곳 지정(프로젝트의 static파일명)

            do {
                destinationFileName = RandomStringUtils.randomAlphanumeric(32)+ "." + sourceFileNameExtension;
                destinationFile = new File(fileUrl + destinationFileName);
            }while (destinationFile.exists());

            destinationFile.getParentFile().mkdirs();
            mf.transferTo(destinationFile);

            file.setFilename(destinationFileName);
            file.setFileOriName(sourceFileName);
            file.setFileUrl(fileUrl);

            itemService.saveItem(item);
            categoryService.addCategory(item.getId(), categoryId);
            sellService.sell(loginUser.getUserIdx(), item.getId());

            file.setItemId(item.getId());//itemService에서 생성 후 출력댐
            filesService.save(file);
        }

//        Files file = new Files();
//        String sourceFileName = files.getOriginalFilename(); //파일 원본 이름 ex)증명사진.jpg
//        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();//확장자 .앞부분 반환
//        FilenameUtils.removeExtension(sourceFileName);//확장자 .뒷부분 반환
//
//        File destinationFile;
//        String destinationFileName;
//        String fileUrl = "C:\\Users\\chlgu\\IdeaProjects\\togetherMaraket\\src\\main\\resources\\static\\img\\"; // img 저장할곳 지정(프로젝트의 static파일명)
//
//        do {
//            destinationFileName = RandomStringUtils.randomAlphanumeric(32)+ "." + sourceFileNameExtension;
//            destinationFile = new File(fileUrl + destinationFileName);
//        }while (destinationFile.exists());
//
//        destinationFile.getParentFile().mkdirs();
//        files.transferTo(destinationFile);
//
//        file.setFilename(destinationFileName);
//        file.setFileOriName(sourceFileName);
//        file.setFileUrl(fileUrl);
//
//
//
//        itemService.saveItem(item);
//        categoryService.addCategory(item.getId(), categoryId);
//        sellService.sell(loginUser.getUserIdx(), item.getId());
//
//        file.setItemId(item.getId());//itemService에서 생성 후 출력댐
//        filesService.save(file);

        return "redirect:/";
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findSellingItem();
        List<ItemCategory> itemCategories = categoryService.findAllItemCategory();
        List<Files> files = filesService.findAll();

        model.addAttribute("items", items);
        model.addAttribute("itemCategories", itemCategories);
        model.addAttribute("files", files);
        return "items/itemList";
    }

    @GetMapping(value = "/items2")
    public String list2(Model model) {
        List<Item> items = itemService.findSellingItem();
        List<ItemCategory> itemCategories = categoryService.findAllItemCategory();
        List<Files> files = filesService.findAll();

        model.addAttribute("items", items);
        model.addAttribute("itemCategories", itemCategories);
        model.addAttribute("files", files);
        return "items2/itemList2";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);

        ItemForm form = new ItemForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setContents(item.getContents());
        form.setDealForm(item.getDealForm());
        form.setItemLevel(item.getItemLevel());
        form.setEnul(item.getEnul());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") ItemForm form) {
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getContents(), form.getEnul(), form.getDealForm()
                , form.getItemLevel());

        return "redirect:/";
    }

    @GetMapping("items/{itemId}/itemView")
    public String itemView(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);
        List<Files> fileList = filesService.findByItem(itemId);
        model.addAttribute("item", item);
        model.addAttribute("fileList", fileList);
        return "items/itemView";
    }

    @GetMapping("items2/{itemId}/itemView2")
    public String itemView2(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);
        List<Files> fileList = filesService.findByItem(itemId);
        model.addAttribute("item", item);
        model.addAttribute("fileList", fileList);
        return "items2/itemView2";
    }

    @GetMapping("/search")
    public String itemSearchForm(@RequestParam("itemName") String name , @RequestParam("categoryId") Long categoryId
            , @RequestParam("itemLevel") String itemLevel, @RequestParam("dealForm") String dealForm,
                                 @RequestParam("enul") String enul,@RequestParam("manner") Long manner, Model model) {
        List<Item> searchItems = itemService.findByItemName(name);
        List<Item> mannerItems = itemService.findByManner(name);
        List<Files> files = filesService.findAll();

        model.addAttribute("enul", enul);
        model.addAttribute("dealForm", dealForm);
        model.addAttribute("categoryId",categoryId);
        model.addAttribute("itemLevel", itemLevel);
        model.addAttribute("files", files);

        List<ItemCategory> itemCategories = categoryService.findAllItemCategory();
        model.addAttribute("itemCategories", itemCategories);

        if(manner == 1) {
            model.addAttribute("items", mannerItems);
            return "items/itemSearchList";
        }
        model.addAttribute("items", searchItems);

        return "items/itemSearchList";
    }

    @GetMapping("/search2")
    public String itemSearchForm2(@RequestParam("itemName") String name , @RequestParam("categoryId") Long categoryId
            , @RequestParam("itemLevel") String itemLevel, @RequestParam("dealForm") String dealForm,
                                  @RequestParam("enul") String enul,@RequestParam("manner") Long manner, Model model) {
        List<Item> searchItems = itemService.findByItemName(name);
        List<Item> mannerItems = itemService.findByManner(name);
        List<Files> files = filesService.findAll();

        model.addAttribute("enul", enul);
        model.addAttribute("dealForm", dealForm);
        model.addAttribute("categoryId",categoryId);
        model.addAttribute("itemLevel", itemLevel);
        model.addAttribute("files", files);

        List<ItemCategory> itemCategories = categoryService.findAllItemCategory();
        model.addAttribute("itemCategories", itemCategories);

        if(manner == 1) {
            model.addAttribute("items", mannerItems);
            return "items2/itemSearchList2";
        }

        model.addAttribute("items", searchItems);



        return "items2/itemSearchList2";
    }
}