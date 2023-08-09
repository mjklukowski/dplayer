import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SearchResult } from '../model';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.scss']
})
export class SearchResultComponent {
  @Input() result!: SearchResult
  @Output() onAdd = new EventEmitter<SearchResult>()

  get kind() {
    switch(this.result.kind) {
      case "youtube#playlist":
        return "Playlist"
      case "youtube#video":
        return "Video"
      default:
        return null;
    }
  }

  get thumbnail() {
    const thumbnails = this.result.data.thumbnails;
    if(thumbnails) {
      return thumbnails['default'].url
    }
    return null;
  }

  add() {
    this.onAdd.emit(this.result)
  }

}
