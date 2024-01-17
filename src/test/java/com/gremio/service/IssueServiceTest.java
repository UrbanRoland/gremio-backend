package com.gremio.service;

import com.gremio.model.input.IssueInput;
import com.gremio.persistence.entity.Issue;
import com.gremio.persistence.entity.Project;
import com.gremio.repository.ProjectRepository;
import com.gremio.repository.IssueRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Limit;
import org.springframework.graphql.data.query.ScrollSubrange;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @InjectMocks
    private IssueServiceImpl issueService;
    @Mock
    private IssueRepository issueRepository;
    @Mock
    private ProjectRepository projectRepository;
    
    @Test
    public void IssueService_AddIssue_ReturnIssue() {
        final IssueInput issueInput = IssueInput.builder()
            .title("test")
            .due(LocalDateTime.now())
            .build();
        
        final Project project = new Project();
        
        Mockito.when(projectRepository.findById(Mockito.any())).thenReturn(Optional.of(project));
        Mockito.when(issueRepository.save(Mockito.any())).thenReturn(new Issue());
        
        Issue savedIssue = issueService.addIssue(issueInput);
        
        Assertions.assertNotNull(savedIssue);
    }
    
    @Test
    public void IssueService_FindAllIssuesByTitle_ReturnWindow() {
        String title = "Test";
        ScrollPosition position = ScrollPosition.offset();
        ScrollSubrange subrange = ScrollSubrange.create(position,10,true);
        List<Issue> items = Collections.singletonList(Issue.builder().title("Test").build());
    
        Window<Issue> windowMock = Window.from(items, index -> position, true);
        
        Mockito.when(issueRepository.findAllByTitle(
            Mockito.eq(title),
            Mockito.any(ScrollPosition.class),
            Mockito.any(Limit.class),
            Mockito.any(Sort.class)
        )).thenReturn(windowMock);

        Window<Issue> resultWindow = issueService.findAllIssuesByTitle(title, subrange);

        Assertions.assertNotNull(resultWindow);
        Assertions.assertEquals(1, resultWindow.getContent().size());
    }
    
}