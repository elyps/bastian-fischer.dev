import { Component, Input, OnInit } from '@angular/core';
import { CommentService } from '../../services/comment.service';
import {ActivatedRoute, Router} from '@angular/router';
import { HttpClient } from '@angular/common/http';
import {Observable, Subscription} from 'rxjs';
import { Comment } from '../../models/comment.model';
import {CommentHelper} from "../../models/commentHelper.model";
import {ArticleService} from "../../services/article.service";
import {StorageService} from "../../services/storage.service";
import {TagService} from "../../services/tag.service";
import {AuthService} from "../../services/auth.service";
import {EventBusService} from "../../shared/event-bus.service";
import {Tag} from "../../models/tag.model";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent implements OnInit {
  comments: Comment[] = [];
  errorMessage: string | undefined;
  content: string = '';
  articleId: any;

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
  getComments: any;
  // addComment: any;
  newCommentText: string = "";
  commentAuthor: any;
  private eventBusService: any;
  commentsActivated: boolean = true;
  comment: any;

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
    this.articleId = this.route.snapshot.params['id'];
    this.retrieveComments();

    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR') || this.roles.includes('ROLE_ADMIN');
      this.showActions = this.roles.includes('ROLE_MODERATOR') || this.roles.includes('ROLE_ADMIN');

      this.username = user.username;
    }

  }

  retrieveComments(): void {
    if (this.articleId) {
      this.http.get<Comment[]>(`http://localhost:8083/api/v1/articles/${this.articleId}/comments`)
        .subscribe((comments: Comment[]) => {
          this.comments = comments;
        });
    }
  }

  onSubmit() {
    // Create a new comment object with the properties
    const newComment = new CommentHelper(this.content, this.articleId);

    this.commentService.addComment(newComment, this.articleId).subscribe(
      (response) => {
        // Assuming response contains the newly created comment
        // Add the new comment to your comment list
        this.comments.push(response as unknown as Comment); // Type assertion

        // Clear the input fields
        this.content = '';

        // Retrieve comments after adding a new comment
        this.retrieveComments();
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
