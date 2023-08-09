import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Guild } from '../model';
import { GuildService } from '../guild.service';

@Component({
  selector: 'app-guild-panel',
  templateUrl: './guild-panel.component.html',
  styleUrls: ['./guild-panel.component.scss']
})
export class GuildPanelComponent implements OnInit {

  guilds$?: Observable<Guild[]>

  activeGuildId$?: Observable<string | null>

  constructor(private guildService: GuildService) {}

  ngOnInit(): void {
    this.guilds$ = this.guildService.getGuilds();
    this.activeGuildId$ = this.guildService.getActiveGuildId();
  }

}
