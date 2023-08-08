import { Component, OnInit } from '@angular/core';
import { PlaylistService } from '../playlist.service';
import { ActivatedRoute } from '@angular/router';
import { Observable, map } from 'rxjs';
import { GuildService } from 'src/app/guilds/guild.service';
import { Playlist } from '../model';

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.scss']
})
export class PlaylistComponent implements OnInit {

  playlists$?: Observable<Playlist[]>;
  activePlaylist?: Playlist;

  constructor(
    private route: ActivatedRoute,
    private playlistService: PlaylistService
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        map(params => params.get("guildId")),
      )
      .subscribe(guildId => {
        this.playlists$ = this.playlistService.getPlaylists(guildId!)
      })
  }

  selectPlaylist(playlist: Playlist) {
    this.activePlaylist = playlist;
  }

}
