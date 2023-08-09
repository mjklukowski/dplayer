import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Track } from 'src/app/player/model';

@Component({
  selector: 'app-track',
  templateUrl: './track.component.html',
  styleUrls: ['./track.component.scss']
})
export class TrackComponent {
  @Input() track!: Track;
  @Output() onRemove = new EventEmitter<Track>()
  @Output() onPlay = new EventEmitter<Track>()

  remove() {
    this.onRemove.emit(this.track);
  }

  play() {
    this.onPlay.emit(this.track);
  }

}
