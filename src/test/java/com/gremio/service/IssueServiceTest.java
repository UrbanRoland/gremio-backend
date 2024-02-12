package com.gremio.service;

import com.gremio.model.dto.filter.IssueFilter;
import com.gremio.model.input.IssueInput;
import com.gremio.persistence.entity.Issue;
import com.gremio.persistence.entity.Project;
import com.gremio.repository.ProjectRepository;
import com.gremio.repository.IssueRepository;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @Test
    public void IssueService_FindIssueById_ReturnIssue() {
        Long id = 1L;
        Issue issue = Issue.builder().title("Test").build();
        issue.setId(id);
    
        Mockito.when(issueRepository.findById(Mockito.any())).thenReturn(Optional.of(issue));
    
        Issue resultIssue = issueService.findIssueById(id);
        Assertions.assertNotNull(resultIssue);
        Assertions.assertEquals(id, resultIssue.getId());
    
    }

    @Test
    public void IssueService_FindIssueByFilter_ReturnPage() {
        String title = "Test";
        Issue mockIssue = Issue.builder().title(title).description("description").build();
        List<Issue> issues = Collections.singletonList(mockIssue);

        IssueFilter issueFilter = IssueFilter.builder()
            .title(title)
            .description("description")
            .build();
        Pageable pageable = Pageable.unpaged();

        Page<Issue> mockPage = new PageImpl<>(issues);
        Mockito.when(issueRepository.findAll(Mockito.any(Predicate.class), Mockito.any(Pageable.class))).thenReturn(mockPage);

        Page<Issue> resultPage = issueService.findIssuesByFilter(issueFilter, pageable);
        
        Assertions.assertNotNull(resultPage);
        Assertions.assertTrue(resultPage.getContent().contains(mockIssue));
    }

    @Test
    public void IssueService_UpdateIssue_ReturnIssue(){
        Long id = 1L;
        Issue issue = Issue.builder().title("Test").build();
        issue.setId(id);
        
        IssueInput issueInput = IssueInput.builder()
            .title("test")
            .due(LocalDateTime.now())
            .build();
        
        Mockito.when(issueRepository.findById(Mockito.any())).thenReturn(Optional.of(issue));
        Mockito.when(issueRepository.save(Mockito.any())).thenReturn(issue);
        
        Issue updatedIssue = issueService.updateIssue(id, issueInput);
        
        Assertions.assertNotNull(updatedIssue);
        Assertions.assertEquals(id, updatedIssue.getId());
        Assertions.assertEquals(issueInput.title(), updatedIssue.getTitle());
    }
    
    @Test
    public void IssueService_UpdateIssue_ReturnNull() {
        Long id = 1L;
        IssueInput issueInput = IssueInput.builder()
            .title("test")
            .due(LocalDateTime.now())
            .build();
    
        Mockito.when(issueRepository.findById(Mockito.any
            ())).thenReturn(Optional.empty());
    
        Issue updatedIssue = issueService.updateIssue(id, issueInput);
    
        Assertions.assertNull(updatedIssue);
    }
}