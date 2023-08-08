import { Component, Input } from '@angular/core';
import { Track } from 'src/app/player/model';

@Component({
  selector: 'app-playlist-tracks',
  templateUrl: './playlist-tracks.component.html',
  styleUrls: ['./playlist-tracks.component.scss']
})
export class PlaylistTracksComponent {
  @Input() tracks?: Track[] = []
}
