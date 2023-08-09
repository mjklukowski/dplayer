import { AfterViewInit, Component, ElementRef, EventEmitter, HostListener, OnChanges, Output, SimpleChanges, ViewChild } from '@angular/core';

@Component({
  selector: 'app-playlist-new',
  templateUrl: './playlist-new.component.html',
  styleUrls: ['./playlist-new.component.scss']
})
export class PlaylistNewComponent {
  @Output() onSave = new EventEmitter<string>()

  editMode = false;

  name: string = ""

  @HostListener("click") enableEditMode() {
    this.editMode = true;
  }

  onKeyUp(event: KeyboardEvent) {    
    if(event.key == "Escape") {
      this.disableEditMode()
      return;
    }

    if(event.key == "Enter")
      this.save()
  }
  
  save() {
    if(!this.validate(this.name))
      return;
    this.onSave.emit(this.name);
    this.disableEditMode();
  }

  onBlur() {
    if(this.name.trim().length == 0)
      this.disableEditMode();
    else
      this.save()
  }

  validate(name: string): boolean {
    if(name.trim().length == 0)
      return false;
    return true;
  }

  private disableEditMode() {
    this.editMode = false;
    this.name = "";
  }
}
