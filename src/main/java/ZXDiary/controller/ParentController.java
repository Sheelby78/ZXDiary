package ZXDiary.controller;


import ZXDiary.dto.ChildDTO;
import ZXDiary.exception.CustomException;
import ZXDiary.model.Child;
import ZXDiary.model.Parent;
import ZXDiary.service.ParentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "parents")
@RequestMapping("/parents")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_PARENT')")
public class ParentController {
    private final ModelMapper modelMapper;
    @Autowired
    private final ParentService parentService;

    @PostMapping("/child")
    @ApiOperation(value = "Adding new child",
                    response = Child.class,
                    notes = "Parent can only have one child")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Added new child"),
            @ApiResponse(code = 409, message = "Parent can only have 1 child")})
    public Child addChild(HttpServletRequest req, @RequestBody ChildDTO child) {
        Child child1 = modelMapper.map(child, Child.class);
        parentService.addChild(req, child1);
        return child1;
    }

    @PutMapping("/child")
    public Child editChild(HttpServletRequest req, @RequestBody ChildDTO child) {
        Child child1 = modelMapper.map(child, Child.class);
        return parentService.updateChild(req, child1);
    }

    @GetMapping("/child")
    public Child getChild(HttpServletRequest req) {
        Parent parent = parentService.getParentFromRequest(req);
        if(!parent.hasChild()) {
            throw new CustomException("Brak dziecka", HttpStatus.NOT_FOUND);
        }
        return parent.getChild();
    }
}
