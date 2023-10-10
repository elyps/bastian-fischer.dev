import { Component, OnInit } from '@angular/core';
import { TagService } from '../../services/tag.service';
import { Tag } from '../../models/tag.model';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.scss']
})
export class TagsComponent implements OnInit {

  tags: string[] = []; // Use string[] type for storing tag names

  constructor(private route: ActivatedRoute, private tagService: TagService) { }

  ngOnInit(): void {
    this.loadTags();

    // Annahme: Artikel-ID und Titel sind aus der Route verfügbar
    // @ts-ignore
    const articleId = +this.route.snapshot.paramMap.get('articleId');
    const title = this.route.snapshot.paramMap.get('title');

    // Tags für den aktuellen Artikel und Titel abrufen
    this.tagService.getTagsForArticleAndTitle(articleId, title)
      .subscribe(tags => {
        this.tags = tags;
      });

  }

  loadTags(): void {
    this.tagService.getAllTags()
      .subscribe(tags => {
        // Extract the tag names and store them in the tags array
        this.tags = tags.map(tag => {
          return tag.name;
        });
      });
  }

}
