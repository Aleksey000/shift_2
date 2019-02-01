package ru.cft.starterkit.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.starterkit.entity.Item;
import ru.cft.starterkit.entity.User;
import ru.cft.starterkit.exception.ObjectNotFoundException;
import ru.cft.starterkit.service.implement.AuthService;
import ru.cft.starterkit.service.implement.ItemService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private HttpServletRequest request;
    private final ItemService itemService;
    private final AuthService authService;

    @Autowired
    public ItemController(ItemService itemService, AuthService authService) {
        this.itemService = itemService;
        this.authService = authService;
    }

    // Items list
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/",
            produces = "application/json"
    )
    public List<Item> viewAll(
    ) {
        return itemService.list();
    }

    // Items list
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/my-items",
            produces = "application/json"
    )
    public List<Item> myItems(
    ) {
        User user = authService.getCurrentUser(this.request.getSession());
        return itemService.list(user.getId());
    }

    // View item
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/item/{id}",
            produces = "application/json"
    )
    public Item view(
            @PathVariable(name = "id") Long id
    ) {
        try {
            return itemService.get(id);
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage(), e);
        }
    }

    // Delete item
    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/item/{id}",
            produces = "application/json"
    )
    public HashMap<String, Boolean> delete(
            @PathVariable(name = "id") Long id
    ) {
        HashMap<String, Boolean> answer = new HashMap<>();
        try {
            itemService.delete(id);
            answer.put("isDelete", true);
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage(), e);
        }

        return answer;
    }

    // Create item
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/item",
            consumes = "application/x-www-form-urlencoded",
            produces = "application/json"
    )
    public Item create(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "date_to_end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_to_end,
            @RequestParam(name = "price") Long price,
            @RequestParam(name = "description") String description
    ) {
        User user = authService.getCurrentUser(this.request.getSession());
        return itemService.add(title, description, price, date_to_end, user.getId());
    }

    // update item
    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/item/{id}",
            consumes = "application/x-www-form-urlencoded",
            produces = "application/json"
    )
    public Item update(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "date_to_end") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date_to_end,
            @RequestParam(name = "price") Long price,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "status") String status
//            @RequestParam(name = "tenant_id") Long tenant_id
    ) {
        try {
            Item item = itemService.get(id);

            item.setTitle(title);
            item.setDateToEnd(date_to_end);
            item.setPrice(price);
            item.setDescription(description);
            item.setStatus(status);
//            item.setTenantId(tenant_id);

            return itemService.update(item);

        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage(), e);
        }
    }
}
