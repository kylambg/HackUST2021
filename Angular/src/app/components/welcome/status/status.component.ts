import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, switchMap, tap } from 'rxjs/operators';
import { FlashMessageService } from '../../../services/flash-message.service';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styles: [
  ]
})
export class StatusComponent implements OnInit {

  statues: Array<any> = [];
  newStatus;
  getStatusList = this.userService.getUserStatusSearchFn;
  searching = false;
  searchFailed = false;
  noItems = false;

  saving = false;
  statusLoading = true;

  statusFormatter = status => status.name;

  constructor(
    private userService: UserService,
    private fmService: FlashMessageService
  ) { }

  ngOnInit(): void {
    this.userService.getUserStatus().subscribe(statues => {
      this.statues = statues;
      this.statusLoading = false;
    });
  }

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => this.searching = true),
      switchMap(term =>
        this.getStatusList(term).pipe(
          tap((items: any) => {
            this.searchFailed = false;
            this.noItems = items.length <= 0;
          }),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          }))
      ),
      tap(() => this.searching = false)
    )

  addStatus(): void {
    this.saving = true;
    this.userService.addUserStatus(this.newStatus.id).subscribe((statuses) => {
      this.statues = statuses;
      this.newStatus = undefined;
      this.saving = false;
    }, () => {
      this.fmService.sendMessage('Cannot add status. Try again later.', 'danger');
      this.saving = false;
    });
  }

  removeStatus(id): void {
    if (this.saving) {
      return;
    }
    this.saving = true;
    this.userService.removeUserStatus(id).subscribe((statuses) => {
      this.statues = statuses;
      this.saving = false;
    }, () => {
      this.fmService.sendMessage('Cannot remove status. Try again later.', 'danger');
      this.saving = false;
    });
  }
}

