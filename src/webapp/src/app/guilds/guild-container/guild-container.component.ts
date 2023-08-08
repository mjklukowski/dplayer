import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-guild-container',
  templateUrl: './guild-container.component.html',
  styleUrls: ['./guild-container.component.scss']
})
export class GuildContainerComponent implements OnInit {

  guildId?: string | null

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.guildId = params.get("guildId")
    })
  }

}
