import { Component, Input, OnInit } from '@angular/core';
import { Project } from '../../models/project.model';
import { ProjectService } from '../../services/project.service';
import { ActivatedRoute, Router } from '@angular/router';
import { StorageService } from '../../services/storage.service';
import { AuthService } from '../../services/auth.service';
import { Subscription } from 'rxjs';
import { EventBusService } from '../../shared/event-bus.service';
import { TagService } from '../../services/tag.service';
import { Tag } from '../../models/tag.model';
import { CommentService } from '../../services/comment.service';
import { HttpClient } from '@angular/common/http';
import { Comment } from '../../models/comment.model';

@Component({
  selector: 'app-projects-details',
  templateUrl: './projects-details.component.html',
  styleUrls: ['./projects-details.component.scss'],
})
export class ProjectsDetailsComponent {
  currentProject: Project = {
    title: '',
    description: '',
    published: false,
    slug: '',
    // createdAt: new Date(),
    // updatedAt: new Date(),
    __v: 0,
    content: '',
    category: '',
    tags: [],
    url: '',
    // languages: [],
    // images: [],
    // comments: [],
    // likes: [],
    // dislikes: [],
    views: 0,
    meta: {},
  };

  message = '';
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  showActions = false;
  username?: string;
  eventBusSub?: Subscription;
  tags: Tag[] = [];
  articles: any;
  comments: Comment[] = [];
  getComments: any;
  // addComment: any;
  newCommentText: string = '';
  commentAuthor: any;
  private eventBusService: any;
  content: any;
  errorMessage: any;
  commentsActivated: boolean = false;

  constructor(
    private projectService: ProjectService,
    private commentService: CommentService,
    private storageService: StorageService,
    private route: ActivatedRoute,
    private tagService: TagService,
    private authService: AuthService,
    private eventBus: EventBusService,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.route.snapshot.params['slug'] !== undefined) {
      this.message = '';
      this.getProject(this.route.snapshot.params['slug']);
    } else {
      this.message = '400 - No article selected. Please select an article.';
    }

    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard =
        this.roles.includes('ROLE_MODERATOR') ||
        this.roles.includes('ROLE_ADMIN');
      this.showActions =
        this.roles.includes('ROLE_MODERATOR') ||
        this.roles.includes('ROLE_ADMIN');

      this.username = user.username;
    }
  }

  getProject(slug: string): void {
    console.log('getProject() called', slug);

    this.projectService.getProjectBySlug(slug).subscribe({
      next: (data) => {
        console.log(data);
        console.log(data.id);
        this.currentProject = data;
        // this.getComments(data.id);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
