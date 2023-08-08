import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GuildContainerComponent } from './guild-container/guild-container.component';

const routes: Routes = [
  {
    path: ":guildId",
    component: GuildContainerComponent,
    children: [
      {
        path: "",
        loadChildren: () => import("../queue/queue.module").then(m => m.QueueModule)
      },
      {
        path: "",
        loadChildren: () => import("../playlist/playlist.module").then(m => m.PlaylistModule)
      }
    ]
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GuildRoutingModule { }
