import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GuildPanelComponent } from './guild-panel/guild-panel.component';
import { GuildComponent } from './guild/guild.component';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    GuildPanelComponent,
    GuildComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    GuildPanelComponent
  ]
})
export class GuildsModule { }
