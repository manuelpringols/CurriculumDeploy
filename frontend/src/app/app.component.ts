import { ChangeDetectorRef, Component, ElementRef, HostListener, Inject, PLATFORM_ID, Renderer2 } from '@angular/core';
import { SpotifyService } from './servizi/spotify.service';
import { isPlatformBrowser } from '@angular/common';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { NgsRevealModule } from 'ngx-scrollreveal';
import { HttpService } from './servizi/http.service';
import { AuthService } from './servizi/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  animations: [
    trigger('fadeIn', [
      state('hidden', style({ opacity: 0, transform: 'translateY(20px)' })),
      state('visible', style({ opacity: 1, transform: 'translateY(40px)' })),
      transition('hidden => visible', animate('1600ms ease-out')),
      transition('visible => hidden', animate('1600ms ease-out')),
    ])
  ]
})
export class AppComponent {
[x: string]: any;
toggleMenu() {
  this.menuOpen = !this.menuOpen;
}
username: any;
password: any;

isAuthenticated: boolean=false;

isPlaying: boolean= false;
state: string = 'hidden';
jwtToken: any;
constructor(private spotifyService: SpotifyService,
  @Inject(PLATFORM_ID) private platformId: Object,
  private el: ElementRef,
  private renderer: Renderer2,
  private http:HttpService,
  private authService:AuthService,
  private cdr : ChangeDetectorRef) {}



  title = 'sito_curriculum';
minHeight: any;
trackId = '7H7NyZ3G075GqPx2evsfeb';

projectInformation1:any;

projectInformation2:any;

projectInformation3:any;

projectInformation4:any;




menuOpen = false;

onAuthStatusChange(authStatus: boolean) {
  this.isAuthenticated = authStatus; // Qui authStatus deve essere un booleano
}



scrollToBackend() {
  const backendSection = document.getElementById('app-backend');
  if (backendSection) {
    backendSection.scrollIntoView({ behavior: 'smooth' });
  }

  console.log("mim")
}

  scrollToSection(sectionId: string) {
    const element = document.getElementById(sectionId);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }


  ngOnInit(){

    this.isAuthenticated=this.authService.isTokenValid();
    console.log("MARIO BIONDI token " + this.isAuthenticated)

    if(this.isAuthenticated){
      this.getProjectDescription1();
      this.getProjectDescription2();
      this.getProjectDescription3()
      this.getProjectDescription4()

      console.log("MARIO BIONDI token " + this.isAuthenticated + "cappellano")

    }

  }

  ngAfterViewInit(){
    if (isPlatformBrowser(this.platformId)) {
      // Assicurati che `document` e `window` siano disponibili solo nel browser
      this.spotifyService.createSpotifyPlayer('embed-iframe', 'spotify:playlist:37i9dQZF1DXcBWIGoYBM5M');
    }



  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    const componentPosition = this.el.nativeElement.offsetTop;
    const scrollPosition = window.pageYOffset + window.innerHeight;

    // Se la sezione è visibile nello schermo, attiva l'animazione
    if (scrollPosition >= componentPosition + 50) {
      this.state = 'visible';
    } else {
      this.state = 'hidden';
    }
  }

  getProjectDescription1(){
    this.http.getProjectDescription(1).subscribe((dati: any) => {
      console.log('dati ricevuti', dati);
      this.projectInformation1 = dati

      });


  }


  getProjectDescription2(){
    this.http.getProjectDescription(2).subscribe((dati: any) => {
      console.log('dati ricevuti', dati);
      this.projectInformation2 = dati

      });


  }

  getProjectDescription3(){
    this.http.getProjectDescription(3).subscribe((dati: any) => {
      console.log('dati ricevuti', dati);
      this.projectInformation3 = dati

      });


  }

  getProjectDescription4(){
    this.http.getProjectDescription(4).subscribe((dati: any) => {
      console.log('dati ricevuti', dati);
      this.projectInformation4 = dati

      });


  }

  handleData(data: any) {

    this.projectInformation1 = data[1];
    this.projectInformation2 = data[0];
    this.cdr.detectChanges()
    console.log('Dati ricevuti dal componente figlio:', data);
  }






authenticate() {
  throw new Error('Method not implemented.');
  }
  showAuthModal() {
  throw new Error('Method not implemented.');
  }
  login() {
  throw new Error('Method not implemented.');
  }
  register() {
  throw new Error('Method not implemented.');
  }


}









