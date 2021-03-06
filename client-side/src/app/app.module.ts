import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';
import {LoginPage} from '../pages/login/login';
import {AdminPage} from '../pages/admin/admin';
import {ImgUploadPage} from '../pages/imgUpload/imgUpload';
import {MapviewPage} from '../pages/mapview/mapview';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import {MapClickMenuPage} from '../pages/mapclickmenu/mapclickmenu';
import { AddReviewPage } from '../pages/add-review/add-review';
import {SignupPage} from '../pages/signup/signup';
import {UserPagePage} from '../pages/user-page/user-page';
import {LoginService} from '../pages/login/LoginService';
import {AdminService} from '../pages/admin/adminService';
import {ImgUploadService} from '../pages/imgUpload/imgUploadService';
import {NavigationService} from '../pages/navigation/navigationService';
import { NavigationPage } from '../pages/navigation/navigation';
import { AddReviewService } from '../pages/add-review/AddReviewService';
import { IonRating } from '../components/ion-rating/ion-rating';
import {ComplexSearchPage} from '../pages/complex-search/complex-search';
import {ComplexSearchService} from '../pages/complex-search/complexSearchService';
import {SearchService} from '../pages/mapview/searchService';
import {AddSearchQueryPage} from '../pages/addSearchQueryMenu/addsearchquerymenu';
import {UserInformationService} from '../pages/user-page/userInformationService';
import { LocationListPage } from '../pages/location-list/location-list';
import { ViewSearchQueryPage } from '../pages/viewSearchQuery/viewsearchquery';
import { GetReviewsPage } from '../pages/reviews/reviews';
import { HelpfulUsersPage } from '../pages/helpfulUsers/helpfulUsers';
import { MostRatedLocsPage } from '../pages/mostRatedLocs/mostRatedLocs';
import {GetReviewsService} from '../pages/reviews/ReviewsService';
import { navigationManeuverPage } from '../pages/navigation_maneuver/navigation_maneuver';
import { nerrativeMapPage } from '../pages/navigation_maneuver/nerrativeMap/nerrativeMap';
import { LocationsInRadiusService } from '../pages/location-list/LocationsInRadiusService';
import { AddLocationPage } from '../pages/add-location/add-location';
import { AddLocationService } from '../pages/add-location/AddLocationService';
import { File } from '@ionic-native/file';
import { Camera } from '@ionic-native/camera';
import { AddSearchQueryService } from '../pages/addSearchQueryMenu/AddSearchQueryService';
import {CommentPage} from '../pages/comment/comment';
import {CommentService} from "../pages/comment/CommentService";
import {SpecialConstants} from "../pages/special-constants/special-constants"

@NgModule({
  declarations: [
    MyApp,
    LoginPage,
	  MapviewPage,
	  MapClickMenuPage,
	  AddReviewPage,
	  SignupPage,
	  UserPagePage,
	  NavigationPage,
	  ComplexSearchPage,
	  IonRating,
	  AddSearchQueryPage,
	  LocationListPage,
	  AdminPage,
	  ViewSearchQueryPage,
	  GetReviewsPage,
	  navigationManeuverPage,
	  HelpfulUsersPage,
	  AddLocationPage,
	  MostRatedLocsPage,
	  nerrativeMapPage,
	  ImgUploadPage,
    CommentPage
  ],
  imports: [
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
	  LoginPage,
	  MapviewPage,
	  MapClickMenuPage,
	  AddReviewPage,
	  SignupPage,
	  UserPagePage,
	  NavigationPage,
	  ComplexSearchPage,
	  AddSearchQueryPage,
	  LocationListPage,
	  AdminPage,
	  ViewSearchQueryPage,
	  GetReviewsPage,
	  navigationManeuverPage,
	  HelpfulUsersPage,
	  AddLocationPage,
	  MostRatedLocsPage,
	  nerrativeMapPage,
	  ImgUploadPage,
    CommentPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
	  LoginService,
	  NavigationService,
	  AddReviewService,
	  SearchService,
	  ComplexSearchService,
	  UserInformationService,
	  AdminService,
	  GetReviewsService,
	  LocationsInRadiusService,
	  AddLocationService,
	  ImgUploadService,
	  File,
    Camera,
    CommentService,
    AddSearchQueryService,
    SpecialConstants
  ]
})
export class AppModule {}
