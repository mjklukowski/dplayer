import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GuildPanelComponent } from './guild-panel/guild-panel.component';
import { GuildComponent } from './guild/guild.component';



@NgModule({
  declarations: [
    GuildPanelComponent,
    GuildComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    GuildPanelComponent
  ]
})
export class GuildsModule { }
