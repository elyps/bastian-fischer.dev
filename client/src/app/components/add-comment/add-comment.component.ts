import { Component, OnInit } from '@angular/core';
import { CommentService } from '../../services/comment.service';
import { CommentHelper } from '../../models/commentHelper.model';
import { ActivatedRoute } from "@angular/router";
import { HttpClient } from "@angular/common/http";

interface Comment {
  id: number;
  content: string;
}

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
})
export class AddCommentComponent implements OnInit {
  content: any;
  articleId: string = '';
  commentList: any[] = [];
  comments: Comment[] = []; // Kommentarliste

  constructor(private commentService: CommentService, private route: ActivatedRoute, private http: HttpClient) {
  }

  ngOnInit(): void {
    this.articleId = this.route.snapshot.params['id'];
    this.retrieveComments();
  }

  /*onSubmit() {
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
  }*/


  retrieveComments(): void {
    this.http.get<Comment[]>(`http://localhost:8083/api/v1/articles/${this.articleId}/comments`)
      .subscribe((comments: Comment[]) => {
        this.comments = comments;
      });
  }
}
