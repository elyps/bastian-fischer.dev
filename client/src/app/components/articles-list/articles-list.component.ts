import {Component, OnInit} from '@angular/core';
import {Article} from "../../models/article.model";
import {ArticleService} from "../../services/article.service";

@Component({
    selector: 'app-articles-list',
    templateUrl: './articles-list.component.html',
    styleUrls: ['./articles-list.component.scss']
})
export class ArticlesListComponent implements OnInit {

    articles: Article[] = [];
    currentArticle: Article = {};
    currentIndex = -1;
    title = '';

    page = 1;
    count = 0;
    pageSize = 5;
    pageSizes = [5, 10, 15, 20];
    private router: any;

    constructor(private articleService: ArticleService) {
    }

    ngOnInit(): void {
        this.retrieveArticles();
    }

    /*retrieveArticles(): void {
      this.articleService.getAll()
          .subscribe({
            next: (data) => {
              this.articles = data;
              console.log(data);
            },
            error: (e) => console.error(e)
          });
    }*/

    getRequestParams(searchTitle: string, page: number, pageSize: number): any {
        let params: any = {};

        if (searchTitle) {
            params[`title`] = searchTitle;
        }

        if (page) {
            params[`page`] = page - 1;
        }

        if (pageSize) {
            params[`size`] = pageSize;
        }

        return params;
    }

    retrieveArticles(): void {
        const params = this.getRequestParams(this.title, this.page, this.pageSize);

        this.articleService.getAll(params)
            .subscribe(
                response => {
                    const {articles, totalItems} = response;
                    this.articles = articles;
                    this.count = totalItems;
                    console.log(response);
                },
                error => {
                    console.log(error);
                });
    }

    refreshList(): void {
        this.retrieveArticles();
        this.currentArticle = {};
        this.currentIndex = -1;
    }

    setActiveArticle(article: Article, index: number): void {
        this.currentArticle = article;
        this.currentIndex = index;
    }

    /*setActiveArticle(article: Article, index: number): void {
        this.router.navigate(['/article', article.id]);
    }*/

    removeAllArticles(): void {
        this.articleService.deleteAll()
            .subscribe({
                next: (res) => {
                    console.log(res);
                    this.refreshList();
                },
                error: (e) => console.error(e)
            });
    }

    handlePageChange(event: number): void {
        this.page = event;
        this.retrieveArticles();
    }

    handlePageSizeChange(event: any): void {
        this.pageSize = event.target.value;
        this.page = 1;
        this.retrieveArticles();
    }

    searchTitle(): void {
        this.page = 1;
        this.retrieveArticles();
    }

    /*searchTitle(): void {
      this.currentArticle = {};
      this.currentIndex = -1;

      this.articleService.findByTitle(this.title)
          .subscribe({
            next: (data) => {
              this.articles = data;
              console.log(data);
            },
            error: (e) => console.error(e)
          });
    }*/
}
