import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QueueComponent } from './queue/queue.component';
import { QueueRoutingModule } from './queue-routing.module';
import { TrackComponent } from '../common/track/track.component';



@NgModule({
  declarations: [
    QueueComponent,
    TrackComponent
  ],
  imports: [
    CommonModule,
    QueueRoutingModule
  ]
})
export class QueueModule { }
