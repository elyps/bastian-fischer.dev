import { Component, OnInit } from '@angular/core';
import { Article } from 'src/app/models/article.model';
import { ArticleService } from 'src/app/services/article.service';

@Component({
    selector: 'app-add-article',
    templateUrl: './add-article.component.html',
    styleUrls: ['./add-article.component.scss']
})
export class AddArticleComponent implements OnInit {

    article: Article = {
        slug: '',
        title: '',
        content: '',
        category: '',
        tags: [],
        images: [],
        description: '',
        url: '',
        languages: []
    };
    submitted = false;

    constructor(private articleService: ArticleService) {
    }

    ngOnInit(): void {
        this.article = {
            slug: '',
            title: '',
            content: '',
            category: '',
            tags: [],
            images: [],
            description: '',
            url: '',
            languages: []
        };
    }

    saveArticle(): void {
        // Split the comma-separated tags into an array
        // @ts-ignore
        this.article.tags = this.article.tags.split(',').map((tag: string) => tag.trim());
        // Split the comma-separated images into an array
        // @ts-ignore
        this.article.images = this.article.images.split(',').map((image: string) => image.trim());
        // Split the comma-separated languages into an array
        // @ts-ignore
        this.article.languages = this.article.languages.split(',').map((language: string) => language.trim());


        // The rest of your code for saving the article...
        const data = {
            slug: this.article.slug,
            title: this.article.title,
            content: this.article.content,
            category: this.article.category,
            tags: this.article.tags, // Now it's an array
            images: this.article.images,
            description: this.article.description,
            url: this.article.url,
            languages: this.article.languages
        };

        this.articleService.create(data)
            .subscribe({
                next: (res) => {
                    console.log(res);
                    this.submitted = true;
                },
                error: (e) => console.error(e)
            });
    }


    newArticle(): void {
        this.submitted = false;
        this.article = {
            slug: this.articleService.getArticleUrl(this.article.title),
            title: '',
            content: '',
            category: '',
            tags: [],
            images: [],
            description: '',
            url: this.articleService.getArticleUrl(this.article.title),
            languages: []
        };
    }

}
