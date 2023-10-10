import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { httpInterceptorProviders } from "./helpers/http.interceptor";
import { NgxPaginationModule } from 'ngx-pagination';
import { CarouselModule } from 'ngx-bootstrap/carousel';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddArticleComponent } from './components/add-article/add-article.component';
import { ArticleDetailsComponent } from './components/article-details/article-details.component';
import { ArticlesListComponent } from './components/articles-list/articles-list.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { BoardAdminComponent } from './components/board-admin/board-admin.component';
import { BoardModeratorComponent } from './components/board-moderator/board-moderator.component';

import { BoardUserComponent } from './components/board-user/board-user.component';
import { EditArticleComponent } from './components/edit-article/edit-article.component';
import { AboutMeComponent } from './components/about-me/about-me.component';
import { FooterComponent } from './components/footer/footer.component';
import { TagsComponent } from './components/tags/tags.component';
import { CommentComponent } from './components/comment/comment.component';
import { AddCommentComponent } from './components/add-comment/add-comment.component';
import { GithubBannerComponent } from './components/github-banner/github-banner.component';
import { ContactFormSliderComponent } from './components/contact-form-slider/contact-form-slider.component';
import { ContactComponent } from './components/contact/contact.component';
import { CvComponent } from './components/cv/cv.component';
import { PinInputComponent } from './components/pin-input/pin-input.component';
import { RecruiterComponent } from './components/recruiter/recruiter.component';
import { RecruiterDashboardComponent } from './components/recruiter-dashboard/recruiter-dashboard.component';
import { CertsComponent } from './components/certs/certs.component';
import { VersionComponent } from './components/version/version.component';
import { GitInfoComponent } from './components/git-info/git-info.component';
import { RepositoriesComponent } from './components/repositories/repositories.component';
import { ProjectsDetailsComponent } from './components/projects-details/projects-details.component';
import { RepositoryDetailsComponent } from './components/repository-details/repository-details.component';
import { ImpressComponent } from './components/impress/impress.component';
import { PrivacyComponent } from './components/privacy/privacy.component';
import { ProjectsListComponent } from './components/projects-list/projects-list.component';
import { CookiePopupComponent } from './components/cookie-popup/cookie-popup.component';
import { CookieSettingsModalComponent } from './components/cookie-settings-modal/cookie-settings-modal.component';
import { CustomerPortalComponent } from './components/customer-portal/customer-portal.component';

@NgModule({
  declarations: [
    AppComponent,
    AddArticleComponent,
    ArticleDetailsComponent,
    ArticlesListComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    EditArticleComponent,
    AboutMeComponent,
    FooterComponent,
    TagsComponent,
    CommentComponent,
    AddCommentComponent,
    GithubBannerComponent,
    ContactFormSliderComponent,
    ContactComponent,
    CvComponent,
    PinInputComponent,
    RecruiterComponent,
    RecruiterDashboardComponent,
    CertsComponent,
    VersionComponent,
    GitInfoComponent,
    RepositoriesComponent,
    ProjectsDetailsComponent,
    RepositoryDetailsComponent,
    ImpressComponent,
    PrivacyComponent,
    ProjectsListComponent,
    CookiePopupComponent,
    CookieSettingsModalComponent,
    CustomerPortalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgxPaginationModule,
    CarouselModule.forRoot(),
    ReactiveFormsModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
