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

  selectPlaylist(playlist: Playlist) {
    this.onChange.emit(playlist);
  }

}
