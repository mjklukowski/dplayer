import { Component, Input, OnInit } from '@angular/core';
import { Channel, Guild } from '../model';
import { Observable } from 'rxjs';
import { GuildService } from '../guild.service';

@Component({
  selector: 'app-guild',
  templateUrl: './guild.component.html',
  styleUrls: ['./guild.component.scss']
})
export class GuildComponent implements OnInit {
  @Input() guild!: Guild
  
  collapsed = true;
  channels$?: Observable<Channel[]>

  activeChannelId$?: Observable<string | null>
  
  constructor(private guildService: GuildService) {}

  ngOnInit(): void {
    this.channels$ = this.guildService.getChannels(this.guild);
    this.activeChannelId$ = this.guildService.getActiveChannelId();
  }

  toggleCollapse() {
    this.collapsed = !this.collapsed;
  }

}
