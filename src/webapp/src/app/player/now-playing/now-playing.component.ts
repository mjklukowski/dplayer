import { Component, Input } from '@angular/core';
import { Track } from '../model';
import { Channel, Guild } from 'src/app/guilds/model';

@Component({
  selector: 'app-now-playing',
  templateUrl: './now-playing.component.html',
  styleUrls: ['./now-playing.component.scss']
})
export class NowPlayingComponent {
  @Input() track?: Track | null
  @Input() channel?: Channel | null
  @Input() guild?: Guild | null
}
