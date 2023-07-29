package ZXDiary.controller;


import ZXDiary.dto.ChildDTO;
import ZXDiary.exception.CustomException;
import ZXDiary.model.Child;
import ZXDiary.model.Parent;
import ZXDiary.model.Therapist;
import ZXDiary.service.TherapistService;
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
import java.util.Set;

@RestController
@Api(tags = "therapist")
@RequestMapping("/therapists")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_THERAPIST')")
public class TherapistController {
    private final ModelMapper modelMapper;
    @Autowired
    private final TherapistService therapistService;

    @GetMapping("/children")
    public Set<Child> getChildren(HttpServletRequest req) {
        Therapist therapist = therapistService.getTherapistFromRequest(req);
        return therapist.getChildSet();
    }

    @GetMapping("children/{id}")
    public Child getChild(HttpServletRequest req, @PathVariable Integer id){
        return therapistService.getChild(req, id);
    }


    @PostMapping("/children/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Added the specified child"),
            @ApiResponse(code = 404, message = "Child of this ID does not exist"),
            @ApiResponse(code = 409, message = "Child of this ID is already signed to the therapist")})
    public Child addChild(HttpServletRequest req, @PathVariable Integer id) {
        return therapistService.addChild(req, id);
    }

    @DeleteMapping("/children/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Removed the specified child"),
            @ApiResponse(code = 404, message = "Child of this ID does not exist"),
            @ApiResponse(code = 409, message = "Child of this ID is not signed to the therapist")})
    public Child removeChild(HttpServletRequest req, @PathVariable Integer id) {
        return therapistService.removeChild(req, id);
    }
}
