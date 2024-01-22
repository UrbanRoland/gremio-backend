package com.gremio.controller;

import com.gremio.model.input.ProjectInput;
import com.gremio.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    
    @MutationMapping
    public void createProject(@Argument final ProjectInput project) {
        projectService.createProject(project);
    }
}