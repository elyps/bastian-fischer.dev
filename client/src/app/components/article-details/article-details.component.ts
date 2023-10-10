import {Component, Input, OnInit} from '@angular/core';
import {Article} from "../../models/article.model";
import {ArticleService} from "../../services/article.service";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../services/storage.service";
import {AuthService} from "../../services/auth.service";
import {Subscription} from "rxjs";
import {EventBusService} from "../../shared/event-bus.service";
import {TagService} from "../../services/tag.service";
import {Tag} from "../../models/tag.model";
import {CommentService} from "../../services/comment.service";
import {HttpClient} from "@angular/common/http";
import {Comment} from "../../models/comment.model";

@Component({
  selector: 'app-article-details',
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.scss'],
})
export class ArticleDetailsComponent implements OnInit {
  // @Input() viewMode = false;

  /*@Input() currentArticle: Article = {
      title: '',
      description: '',
      published: false,
      // slug: '',
      // createdAt: new Date(),
      // updatedAt: new Date(),
      // __v: 0,
      // content: '',
      // category: '',
      // tags: [],
  };*/

  currentArticle: Article = {
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
    languages: [],
    images: [],
    comments: [],
    likes: [],
    dislikes: [],
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
  commentsActivated: boolean = true;

  constructor(
    private articleService: ArticleService,
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
      this.getArticle(this.route.snapshot.params['slug']);
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

    /*this.eventBusSub = this.eventBusService.on('logout', () => {
      this.logout();
    });*/

    // this.loadTags();

    /* // Holen Sie die Artikel-ID aus den Routenparametern
     const articleId = this.route.snapshot.params['id'];
     if (articleId) {
       this.http.get<Comment[]>(`http://localhost:8083/api/v1/articles/${articleId}/comments`)
         .subscribe(comments => {
             if (comments) {
               this.comments = comments;
             } else {
               console.error('No comments were found for the article.');
               this.errorMessage = 'No comments were found for the article.';
             }
           },
           error => {
             console.error('An error occurred while fetching comments:', error);
             this.errorMessage = 'An error occurred while fetching comments. Please try again later.';
           });
     } else {
       console.error('No article ID was provided.');
       this.errorMessage = 'No article ID was provided.';
     }*/
  }

  // find article id by article slug
  getArticleIdBySlug(slug: string): void {
    this.articleService.getArticleIdBySlug(slug).subscribe({
      next: (data) => {
        console.log(data);
        this.getComments(data);
        console.log('getArticle() called', data);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  // find article by article slug
  getArticle(slug: string): void {
    console.log('getArticle() called', slug);

    this.articleService.getArticleBySlug(slug).subscribe({
      next: (data) => {
        console.log(data);
        console.log(data.id);
        this.currentArticle = data;
        // this.getComments(data.id);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

/*   loadTags(): void {
    this.tagService.getAllTags().subscribe((tags) => {
      this.tags = tags;
    });
  } */

  logout(): void {
    this.storageService.clean();
    this.authService.logout().subscribe({
      next: (res) => {
        console.log(res);
        // window.location.reload();
        window.location.href = '/';
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  deleteArticle(): void {
    this.articleService.delete(this.currentArticle.id).subscribe({
      next: () => {
        this.router.navigate(['/articles']);
      },
      error: (e) => console.error(e),
    });
  }

  updateArticle(): void {
    this.articleService
      .update(this.currentArticle.id, this.currentArticle)
      .subscribe({
        next: () => {
          this.router.navigate(['/articles']);
        },
        error: (e) => console.error(e),
      });
  }
}
