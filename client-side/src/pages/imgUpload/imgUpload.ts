import { Component } from '@angular/core';
import { NavController, ModalController, NavParams, LoadingController, Events, AlertController ,ToastController} from 'ionic-angular';
import { Camera, CameraOptions } from '@ionic-native/camera';
import { File, FileEntry } from '@ionic-native/file';
import { ImgUploadService } from './imgUploadService';


@Component({
  selector: 'page-imgUpload',
  templateUrl: 'imgUpload.html',
  
  
})

export class ImgUploadPage {
  myPhoto: any;
  error: any;
  loading: any;
  
  constructor(public navCtrl: NavController, public navParams: NavParams,
			  public modalCtrl: ModalController,
			  public loadingCtrl: LoadingController, public events: Events,
			  public alertCtrl: AlertController, private camera: Camera,
			  private file:File, private imgUploadService: ImgUploadService,
			  private toastCtrl: ToastController) {
		}
  
  
  takePhoto() {
    this.camera.getPicture({
      quality: 100,
      destinationType: this.camera.DestinationType.FILE_URI,
      sourceType: this.camera.PictureSourceType.CAMERA,
      encodingType: this.camera.EncodingType.PNG,
      saveToPhotoAlbum: true
    }).then(imageData => {
      this.myPhoto = imageData;
      this.uploadPhoto(imageData);
    }, error => {
      this.error = JSON.stringify(error);
    });
  }

  selectPhoto(): void {
    this.camera.getPicture({
      sourceType: this.camera.PictureSourceType.PHOTOLIBRARY,
      destinationType: this.camera.DestinationType.FILE_URI,
      quality: 100,
      encodingType: this.camera.EncodingType.PNG,
    }).then(imageData => {
      this.myPhoto = imageData;
      this.uploadPhoto(imageData);
    }, error => {
      this.error = JSON.stringify(error);
    });
  }
  
  uploadPhoto(imageFileUri: any): void {
	this.error = null;
    this.loading = this.loadingCtrl.create({
      content: 'Uploading...'
    });

    this.loading.present();

    this.file.resolveLocalFilesystemUrl(imageFileUri)
      .then(entry => (<FileEntry>entry).file(file => this.readFile(file)))
      .catch(err => console.log(err));
  }
  
  private readFile(file: any) {
    const reader = new FileReader();
    reader.onloadend = () => {
      const formData = new FormData();
      const imgBlob = new Blob([reader.result], {type: file.type});
      formData.append('file', imgBlob, file.name);
      this.postData(formData);
    };
    reader.readAsArrayBuffer(file);
  }
  
  private postData(formData: FormData) {
	this.imgUploadService.upload(formData).subscribe(
	res => {
		this.presentToast('image uploaded successfully');
		this.loading.dismiss();
	}
	, err => {
		this.handleError(err);
		this.loading.dismiss();
	}		);
  
  }
  
  presentToast(msg) {
  let toast = this.toastCtrl.create({
    message: msg,
    duration: 3000,
    position: 'top'
  });

  toast.onDidDismiss(() => {
    console.log('Dismissed toast');
  });

  toast.present();
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
		this.presentAlert("error is: " + err.error + " message is: " + err.message);
	}
  
}
