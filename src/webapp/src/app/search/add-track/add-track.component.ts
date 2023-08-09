import { Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
import { SearchService } from '../search.service';
import { Observable, of } from 'rxjs';
import { SearchResult } from '../model';

@Component({
  selector: 'app-add-track',
  templateUrl: './add-track.component.html',
  styleUrls: ['./add-track.component.scss']
})
export class AddTrackComponent {

  @ViewChild("dialog") dialog!: ElementRef<HTMLDialogElement>;
  @Output() onAdd = new EventEmitter<string>()

  query: string = ""
  results$?: Observable<SearchResult[]>;

  constructor(private searchService: SearchService) {}

  show() {
    this.dialog.nativeElement.showModal();
  }

  close() {
    this.dialog.nativeElement.close();
  }

  search() {
    if(this.isURL(this.query)) {
      this.results$ = of([{
        kind: "unknown",
        url: this.query,
        data: {
          title: this.query
        }
      }])
      return
    }
    
      this.results$ = this.searchService.search(this.query);
  }

  private isURL(query: string) {
    return /^https?:\/\/.*?$/.test(query)
  }

  add(result: SearchResult) {
    this.onAdd.emit(result.url);
  }

}
