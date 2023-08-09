import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QueueComponent } from './queue/queue.component';
import { QueueRoutingModule } from './queue-routing.module';
import { CommonsModule } from '../commons/commons.module';
import { SearchModule } from '../search/search.module';



@NgModule({
  declarations: [
    QueueComponent
  ],
  imports: [
    CommonModule,
    QueueRoutingModule,
    CommonsModule,
    SearchModule
  ]
})
export class QueueModule { }
