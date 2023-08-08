import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QueueComponent } from './queue/queue.component';
import { QueueRoutingModule } from './queue-routing.module';
import { CommonsModule } from '../commons/commons.module';



@NgModule({
  declarations: [
    QueueComponent
  ],
  imports: [
    CommonModule,
    QueueRoutingModule,
    CommonsModule
  ]
})
export class QueueModule { }
