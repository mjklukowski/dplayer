import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlaylistComponent } from './playlist/playlist.component';
import { PlaylistRoutingModule } from './playlist-routing.module';
import { PlaylistListComponent } from './playlist-list/playlist-list.component';
import { PlaylistTracksComponent } from './playlist-tracks/playlist-tracks.component';
import { CommonsModule } from '../commons/commons.module';
import { PlaylistNewComponent } from './playlist-new/playlist-new.component';
import { FormsModule } from '@angular/forms';
import { SearchModule } from '../search/search.module';



@NgModule({
  declarations: [
    PlaylistComponent,
    PlaylistListComponent,
    PlaylistTracksComponent,
    PlaylistNewComponent
  ],
  imports: [
    CommonModule,
    PlaylistRoutingModule,
    CommonsModule,
    FormsModule,
    SearchModule
  ]
})
export class PlaylistModule { }
