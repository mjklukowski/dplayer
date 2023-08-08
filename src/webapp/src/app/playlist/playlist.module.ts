import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlaylistComponent } from './playlist/playlist.component';
import { PlaylistRoutingModule } from './playlist-routing.module';
import { PlaylistListComponent } from './playlist-list/playlist-list.component';
import { PlaylistTracksComponent } from './playlist-tracks/playlist-tracks.component';
import { CommonsModule } from '../commons/commons.module';



@NgModule({
  declarations: [
    PlaylistComponent,
    PlaylistListComponent,
    PlaylistTracksComponent
  ],
  imports: [
    CommonModule,
    PlaylistRoutingModule,
    CommonsModule
  ]
})
export class PlaylistModule { }
