import { Component, ViewChild, ElementRef } from '@angular/core';
import { NavController, ModalController, NavParams, Events,AlertController, LoadingController } from 'ionic-angular';
import { Geolocation } from '@ionic-native/geolocation';
import { ImgUploadPage } from '../imgUpload/imgUpload';
import { MapClickMenuPage } from '../mapclickmenu/mapclickmenu';
import { ComplexSearchPage } from '../complex-search/complex-search';
import { SearchService } from './searchService';
import { navigationManeuverPage } from '../navigation_maneuver/navigation_maneuver';
import {SpecialConstants} from "../special-constants/special-constants";

declare var google;


@Component({
  selector: 'page-mapview',
  templateUrl: 'mapview.html'
})

/*
	author: Yael Amitay
*/
export class MapviewPage {

  @ViewChild('map') mapElement: ElementRef;
  map: any;
  markers : any;
  geolocation: Geolocation;
  marker_curr_location : any;
  searchQuery: any;
  complexSearchPage = ComplexSearchPage;
  output :  any;
  navigateMarkers : any;
  loading : any;
  userQuery : any;
  pleaseWait : any;

    constructor(public navCtrl: NavController,  public navParams: NavParams, public loadingCtrl: LoadingController, 
			  public alertCtrl: AlertController,public modalCtrl: ModalController, public searchService : SearchService,
			  public events: Events, public _constants : SpecialConstants) {
		this.pleaseWait = true;
		this.loading = this._constants.createCustomLoading();
		this.loading.present();
		this.subscribeToNavigation();
		this.markers = [];
		this.output = navParams.get('adress');
		this.searchQuery = navParams.get('adress');
        this.userQuery = this.navParams.get("myQuery");
		this.loading.dismiss().catch(() => {});
    }

    ionViewDidLoad(){
        this.loadMap(); 
    }

    ionViewDidEnter(){
        google.maps.event.trigger(this.map, 'resize');
	}

	addMarker(LatLngArr){
		for (var i = 0; i < this.markers.length; i++) {
            this.markers[i].setMap(null);
        }
		this.markers = [];
	    for (var i = 0; i < LatLngArr.length; i++) {
            var coords = LatLngArr[i];
            var latLng = new google.maps.LatLng(coords.lat,coords.lng);
            var marker = new google.maps.Marker({
                position: latLng,
                map: this.map
            });
		    google.maps.event.addListener(marker,'click',(event)=>{
			    let clickMenu = this.modalCtrl.create(MapClickMenuPage,{latlng : event.latLng});
			    clickMenu.present();
		    });
			this.markers[i] = marker;
        }
    }

    callSearch(searchQuery) {
		if(searchQuery == '') {
			this.presentAlert('please insert a query');
			return;
		}
  		this.loading = this._constants.createCustomLoading();
		this.loading.present();
  	    this.searchService.search(searchQuery).subscribe(data => {
  			this.addMarker([data.coordinates]);
  			this.map.setCenter(data.coordinates);
			this.loading.dismiss().catch(() => {});
		}, err => {
			this.loading.dismiss().catch(() => {});
			this.handleError(err.json());
  		});
    }

	callToComplexSearch() {
			this.navCtrl.push(this.complexSearchPage);
			let markers_complex = [];
			this.events.subscribe('complexSearch:pressed', (complexSearchResults, initLocation) => {
				for(var i = 0; i < complexSearchResults.length; i++) {
					markers_complex[i] = complexSearchResults[i].coordinates;
				}
				this.addMarker(markers_complex);
				this.map.setCenter(initLocation);
			});
	}

	imgUpload() {
		this.navCtrl.push(ImgUploadPage);
	}

	subscribeToNavigation(){
		this.events.subscribe('navigation:done', (navigationResults,loading) => {
			if(navigationResults == null){
				loading.dismiss().catch(() => {});
				return;
			}
			if(this.navigateMarkers != null){
				this.navigateMarkers[0].setMap(null);
				this.navigateMarkers[1].setMap(null);
				this.navigateMarkers[2].setMap(null);
			}
			var route = new google.maps.Polyline({
			    path: navigationResults.latlng,
			    geodesic: true,
			    strokeColor: '#FF0000',
			    strokeOpacity: 1.0,
			    strokeWeight: 2
			});
            var start_marker = new google.maps.Marker({
                position: navigationResults.latlng[0],
                map: this.map,
				icon: 'assets/icon/start.png'
            });
			var len = navigationResults.latlng.length;
			var end_marker = new google.maps.Marker({
                position: navigationResults.latlng[len-1],
                map: this.map,
				icon: 'assets/icon/end.png'
            });
			route.setMap(this.map);
			this.navigateMarkers = [route, start_marker, end_marker];
			google.maps.event.addListener(route,'click',(event)=>{
				let clickMenu = this.modalCtrl.create(navigationManeuverPage,navigationResults);
				clickMenu.present();
			});
			loading.dismiss().catch(() => {});
		});
	}
	presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Alert',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
	}

	handleError(err) {
		this.presentAlert("error: " + err.message);
    }

	loadMap(){
		this.geolocation = new Geolocation();
		this.geolocation.getCurrentPosition().then((position) => {
			let latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
			let mapOptions = {
				center: latLng,
				zoom: 15,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			}
			this.map = new google.maps.Map(document.getElementById('map'), mapOptions);
			google.maps.event.addListener(this.map,'click',(event)=>{
				let clickMenu = this.modalCtrl.create(MapClickMenuPage,{latlng : event.latLng});
				clickMenu.present();
			} );

			this.trackUser();
			if (!!this.userQuery){
			  this.callSearch(this.userQuery);
			}
			this.pleaseWait = false;
		}, (err) => {
			window.location.reload();
		});
	}

	trackUser() {
		this.geolocation.watchPosition().subscribe((position) => {
			let latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

			if(this.marker_curr_location != null)
				this.marker_curr_location.setMap(null);

			this.marker_curr_location = new google.maps.Marker({
				map: this.map,
				position: latLng,
				icon: 'assets/icon/curr_location.png'
			});
		}, (err) => {
		  console.log(err);
		});
	}

}
