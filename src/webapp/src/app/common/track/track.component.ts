import { Component, Input } from '@angular/core';
import { Track } from 'src/app/player/model';

@Component({
  selector: 'app-track',
  templateUrl: './track.component.html',
  styleUrls: ['./track.component.scss']
})
export class TrackComponent {
  @Input() track!: Track;


}
