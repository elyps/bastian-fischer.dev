import { Component } from '@angular/core';
import { ProjectService } from '../../services/project.service';
import { Project } from '../../models/project.model';

@Component({
  selector: 'app-projects-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss'],
})
export class ProjectsListComponent {
  projects: Project[] = [];
  currentProject: Project = {};
  currentIndex = -1;
  title = '';

  page = 1;
  count = 0;
  pageSize = 6;
  pageSizes = [6, 12, 18, 24];
  private router: any;

  constructor(private projectService: ProjectService) {}

  ngOnInit(): void {
    this.retrieveProjects();
  }

  /*retrieveProjects(): void {
      this.ProjectService.getAll()
          .subscribe({
            next: (data) => {
              this.Projects = data;
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

  retrieveProjects(): void {
    const params = this.getRequestParams(this.title, this.page, this.pageSize);

    this.projectService.getAll(params).subscribe(
      (response) => {
        const { projects, totalItems } = response;
        this.projects = projects;
        this.count = totalItems;
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  refreshList(): void {
    this.retrieveProjects();
    this.currentProject = {};
    this.currentIndex = -1;
  }

  setActiveProject(project: Project, index: number): void {
    this.currentProject = project;
    this.currentIndex = index;
  }

  /*setActiveProject(Project: Project, index: number): void {
        this.router.navigate(['/Project', Project.id]);
    }*/

  removeAllProjects(): void {
    this.projectService.deleteAll().subscribe({
      next: (res) => {
        console.log(res);
        this.refreshList();
      },
      error: (e) => console.error(e),
    });
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveProjects();
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.page = 1;
    this.retrieveProjects();
  }

  searchTitle(): void {
    this.page = 1;
    this.retrieveProjects();
  }

  /*searchTitle(): void {
      this.currentProject = {};
      this.currentIndex = -1;

      this.ProjectService.findByTitle(this.title)
          .subscribe({
            next: (data) => {
              this.Projects = data;
              console.log(data);
            },
            error: (e) => console.error(e)
          });
    }*/
}
