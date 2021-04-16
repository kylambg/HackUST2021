import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { FlashMessageService } from './flash-message.service';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(
        private router: Router,
        private authService: AuthService,
        private fmService: FlashMessageService
    ) { }

    canActivate(route: any): Observable<boolean> {
        const restricted: string = route.data.restricted;
        return new Observable<boolean>((observer) => {
          const user = this.authService.getAuth();
          if (!user) {
                if (!restricted) {
                    observer.next(true);
                } else {
                    this.fmService.sendMessage('You are logged out. Please login to continue.', 'info');
                    this.router.navigate(['/login']);
                    observer.next(false);
                }
            } else {
              if (!restricted) {
                  this.router.navigate(['/']);
                  observer.next(false);
                } else {
                  observer.next(true);
                }
            }
        });
    }

}
