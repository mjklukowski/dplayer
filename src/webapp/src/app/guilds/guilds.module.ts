import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GuildPanelComponent } from './guild-panel/guild-panel.component';
import { GuildComponent } from './guild/guild.component';
import { GuildRoutingModule } from './guild-routing.module';
import { GuildContainerComponent } from './guild-container/guild-container.component';



@NgModule({
  declarations: [
    GuildPanelComponent,
    GuildComponent,
    GuildContainerComponent
  ],
  imports: [
    CommonModule,
    GuildRoutingModule
  ],
  exports: [
    GuildPanelComponent
  ]
})
export class GuildsModule { }
