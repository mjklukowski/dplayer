import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Playlist } from '../model';

@Component({
  selector: 'app-playlist-list',
  templateUrl: './playlist-list.component.html',
  styleUrls: ['./playlist-list.component.scss']
})
export class PlaylistListComponent {
  @Input() playlists?: Playlist[] | null
  @Input() activePlaylist?: Playlist
  @Output() onChange = new EventEmitter<Playlist>();
  @Output() onAdd = new EventEmitter<string>();
  @Output() onRemove = new EventEmitter<Playlist>();

  selectPlaylist(playlist: Playlist) {
    this.onChange.emit(playlist);
  }

  add(name: string) {
    this.onAdd.emit(name);
  }

  remove(playlist: Playlist) {
    this.onRemove.emit(playlist);
  }

}
