import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Track } from 'src/app/player/model';

@Component({
  selector: 'app-playlist-tracks',
  templateUrl: './playlist-tracks.component.html',
  styleUrls: ['./playlist-tracks.component.scss']
})
export class PlaylistTracksComponent {
  @Input() tracks?: Track[] = []
  @Output() onAdd = new EventEmitter<string>()
  @Output() onRemove = new EventEmitter<Track>()
  @Output() onPlay = new EventEmitter<Track>()

  addTrack(trackUrl: string) {
    this.onAdd.emit(trackUrl)
  }

  removeTrack(track: Track) {
    this.onRemove.emit(track);
  }

  playTrack(track: Track) {
    this.onPlay.emit(track)
  }
}
