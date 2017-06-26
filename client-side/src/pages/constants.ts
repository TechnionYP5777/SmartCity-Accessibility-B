import {LoadingController, NavController, NavParams, AlertController, ModalController, ViewController, Events} from 'ionic-angular';

export class Constants {

	public static serverAddress='http://localhost:8080';
	public static userExpiredMessage = "Sorry, it seems you were not active for 60 minutes. Please re-login.";
	public static serverNotResponding = "Looks like the server is not responding, try again later";
	public static notLoggedIn = "Please login to do that!";
	public static generalError = "Something went wrong";

  private static alertCtrl: AlertController;

  public static handleError(err) {
    this.presentAlert("<p> error is: " + err + "</p>");
  }

	public static presentAlert(str) {
    let alert = this.alertCtrl.create({
      title: 'Error',
      subTitle: str,
      buttons: ['OK']
    });
    alert.present();
  }
}
