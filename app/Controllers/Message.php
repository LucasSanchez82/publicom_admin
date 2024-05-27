<<<<<<< Updated upstream
<?php

namespace App\Controllers;

use App\Models\MessageModel;
use App\Models\HistoriqueMessageModel;
use CodeIgniter\Files\File;

class Message extends BaseController
{
    private $messageModel;
    private $historiqueMessageModel;
    public function __construct()
    {
        $this->messageModel = new MessageModel();
        $this->historiqueMessageModel = new HistoriqueMessageModel();
    }
    public function modifierForm($idMessage): string
    {
        $message = $this->messageModel->where(['idMessage' => $idMessage])->first();
        if (!$message) {
            Utilitaires::error('Ce message n\'existe pas');
            return $this->lister();
        }
        return view('message/modifierForm', $message);
    }

    public function modifier()
    {
        $inputValues = [
            "titre" => $this->request->getPost("titre"),
            "message" => $this->request->getPost("message"),
            "enLigne" => $this->request->getPost("enLigne"),
            "image" => $this->request->getFile("imageBackground"),
            "idMessage" => $this->request->getPost("idMessage"),
            'taillePoliceTitre' => (int) $this->request->getPost('policeTailleTitre'),
            'taillePoliceContenu' => (int) $this->request->getPost('policeTailleContenu'),
            'typePoliceTitre' => $this->request->getPost('policeTitre'),
            'typePoliceContenu' => $this->request->getPost('policeContenu'),
            'alignementMessage' => $this->request->getPost('alignementMessage'),
        ];
        if ($inputValues['titre']) {
            $inputValuesToUpdate['titreMessage'] = $inputValues['titre'];
        }
        if ($inputValues['message']) {
            $inputValuesToUpdate['contenuMessage'] = $inputValues['message'];
        }
        if ($inputValues['enLigne']) {
            $inputValuesToUpdate['enLigne'] = (bool) $inputValues['enLigne'];
        }
        $dataUpload = $this->upload();

        if ($dataUpload['isOk']) {
            $inputValuesToUpdate['imageMessage'] = $dataUpload['path'];
        }

        if ($inputValues['taillePoliceTitre']) {
            $inputValuesToUpdate['taillePoliceTitre'] = (int) $inputValues['taillePoliceTitre'];
        }
        if ($inputValues['taillePoliceContenu']) {
            $inputValuesToUpdate['taillePoliceContenu'] = (int) $inputValues['taillePoliceContenu'];
        }
        if ($inputValues['typePoliceTitre']) {
            $inputValuesToUpdate['typePoliceTitre'] = $inputValues['typePoliceTitre'];
        }
        if ($inputValues['typePoliceContenu']) {
            $inputValuesToUpdate['typePoliceContenu'] = $inputValues['typePoliceContenu'];
        }
        if ($inputValues['alignementMessage']) {
            $inputValuesToUpdate['alignementMessage'] = $inputValues['alignementMessage'];
        }

        $this->messageModel->update(['idMessage' => $inputValues['idMessage']], $inputValuesToUpdate);
        return Utilitaires::success('reussi avec  succes', 'liste-messages');
    }

    public function supprimer()  //pas encore d'historique message
    {
        $checkBoxSupprimer = $this->request->getPost("checkboxSupprimer");
        $messageModel = new MessageModel();

        try {
            $historiqueMessageModel = new HistoriqueMessageModel();
            foreach ($checkBoxSupprimer as $currIdMessage) {
                $historiqueMessageModel->where(["IDMESSAGE" => $currIdMessage])->delete();
                $messageModel->delete(["IDMESSAGE" => $currIdMessage]);
            }


            return Utilitaires::success("messages supprimes avec succes");
        } catch (\Exception $err) {
            return Utilitaires::error('Erreur lors de la suppression');
        }
    }


    public function lister(): string
    {
        $messageModel = new MessageModel();
        $data = $messageModel->findAll();

        return view('listeMessage', ['data' => $data]);
    }

    public function visualiserActifs(): string
    {
        $messageModel = new MessageModel();
        $data = $messageModel->where([
            'ENLIGNE' => true,
        ])->findAll();
        // echo json_encode($data);
        // die;
        return view('/message/visualiserMessages', ['data' => $data]);
    }

    public function creerForm()
    {
        return view('message/creerForm');
    }
    public function creer()
    {
        $dataUpload = $this->upload();
        if (!isset($dataUpload['isOk']))
            return Utilitaires::error("Il faut mettre une image");
        if ($dataUpload['isOk']) {
            $relativePath = $dataUpload['path'];
            $data = [
                'idUtilisateur' => session()->get('isConnected')['IDUTILISATEUR'],
                'titreMessage' => $this->request->getPost('titre'),
                'contenuMessage' => $this->request->getPost('message'),
                'imageMessage' => $relativePath,
                'enLigne' => !empty($this->request->getPost('enLigne')),
                'taillePoliceTitre' => (int) $this->request->getPost('policeTailleTitre'),
                'taillePoliceContenu' => (int) $this->request->getPost('policeTailleContenu'),
                'typePoliceTitre' => $this->request->getPost('policeTitre'),
                'typePoliceContenu' => $this->request->getPost('policeContenu'),
                'alignementMessage' => $this->request->getPost('alignementMessage'),
            ];
            try {
                $messageModel = new MessageModel();
                $messageModel->insert($data);
                return Utilitaires::success("Message cree avec succes", "/liste-messages");
                // historisation avec les triggers mysql
            } catch (\Exception $err) {
                return Utilitaires::error("Erreur serveur lors de l'ajout du message");
            }
            // return Utilitaires::success('Message créé avec succès');
        } else {
            return Utilitaires::error($dataUpload['error']);
        }
    }


    private function upload()
    {
        $validationRule = [
            'imageBackground' => [
                'label' => 'Image File',
                'rules' => [
                    'uploaded[imageBackground]',
                    'is_image[imageBackground]',
                    'mime_in[imageBackground,image/jpg,image/jpeg,image/gif,image/png,image/webp]',
                    // 'max_size[imageBackground,100]',
                    // 'max_dims[imageBackground,1024,768]',
                ],
            ],
        ];
        if (!$this->validate($validationRule)) {
            $dataValidator = $this->validator->getErrors();
            // return json_encode($dataValidator);
            return ['isOk' => false, 'error' => 'Le fichier n \'est pas une image'];
        }

        $img = $this->request->getFile('imageBackground');

        if (!$img->hasMoved()) {
            $relativePath = $img->store();
            $filepath = WRITEPATH . 'uploads/' . $relativePath;

            $data = ['uploaded_fileinfo' => new File($filepath)];

            return ['isOk' => true, 'path' => $relativePath];
        }

        return ['isOk' => false, 'error' => 'Le fichier a changé de place'];
    }

    public function showImage($pathFolder, $pathFile)
    {
        $path = $pathFolder . '/' . $pathFile;
        $filepath = WRITEPATH . 'uploads/' . $path;

        $mime = mime_content_type($filepath);
        header('Content-Length: ' . filesize($filepath));
        header("Content-Type: $mime");
        header('Content-Disposition: inline; filename="' . $filepath . '";');
        readfile($filepath);
        exit();

    }

    public function historique($idHistoriquemessage)
    {
        $historiquemessage = $this->historiqueMessageModel->where(['IDMESSAGE' => $idHistoriquemessage])->findAll();
        if (!$historiquemessage) {
            Utilitaires::error('Ce message n\'existe pas');
        }
        return view('/message/historiqueMessage', ['data' => $historiquemessage]);
    }


}
=======
<?php

namespace App\Controllers;

use App\Models\MessageModel;
use App\Models\HistoriqueMessageModel;
use CodeIgniter\Files\File;

class Message extends BaseController
{
    private $messageModel;
    private $historiqueMessageModel;
    public function __construct()
    {
        $this->messageModel = new MessageModel();
        $this->historiqueMessageModel = new HistoriqueMessageModel();
    }
    public function modifierForm($idMessage): string
    {
        $message = $this->messageModel->where(['idMessage' => $idMessage])->first();
        if (!$message) {
            Utilitaires::error('Ce message n\'existe pas');
            return $this->lister();
        }
        return view('message/messageForm', ['action' => '/modifier-message', ...$message]);
    }

    public function modifier()
    {
        return $this->upsert();
    }
    public function creer()
    {
        return $this->upsert();
    }

    public function upsert()
    {
        try {
            $idMessage = $this->request->getPost("idMessage");
            $isUpdate = !empty($idMessage);
            $inputValues = [
                "titre" => $this->request->getPost("titre"),
                "message" => $this->request->getPost("message"),
                "enLigne" => $this->request->getPost("enLigne"),
                "image" => $this->request->getFile("imageBackground"),
                'taillePoliceTitre' => (int) $this->request->getPost('policeTailleTitre'),
                'taillePoliceContenu' => (int) $this->request->getPost('policeTailleContenu'),
                'typePoliceTitre' => $this->request->getPost('policeTitre'),
                'typePoliceContenu' => $this->request->getPost('policeContenu'),
                'alignementMessage' => $this->request->getPost('alignementMessage'),
            ];

            $data = [
                'titreMessage' => $inputValues['titre'],
                'contenuMessage' => $inputValues['message'],
                'enLigne' => (bool) $inputValues['enLigne'],
                'taillePoliceTitre' => $inputValues['taillePoliceTitre'],
                'taillePoliceContenu' => $inputValues['taillePoliceContenu'],
                'typePoliceTitre' => $inputValues['typePoliceTitre'],
                'typePoliceContenu' => $inputValues['typePoliceContenu'],
                'alignementMessage' => $inputValues['alignementMessage'],
            ];

            $isImage = $this->isValidBackgroundImage();

            if ($isImage) {
                $dataUpload = $this->upload();
                if(!$dataUpload['isOk']) {
                    throw new \Exception($dataUpload['error']);
                }
                $data['imageMessage'] = $dataUpload['path'];
            }

            if ($isUpdate) {
                $this->messageModel->update($idMessage, $data);
                $message = "Message modifié avec succès";
            } else {
                $data['idUtilisateur'] = session()->get('isConnected')['IDUTILISATEUR'];
                $this->messageModel->insert($data);
                $message = "Message créé avec succès";
            }
        } catch (\Exception $err) {
            return Utilitaires::error("Erreur serveur lors de l'ajout ou de la modification du message: " . $err->getMessage());
        }

        return Utilitaires::success($message, "/liste-messages");
    }

    public function supprimer()  //pas encore d'historique message
    {
        $checkBoxSupprimer = $this->request->getPost("checkboxSupprimer");
        $messageModel = new MessageModel();

        try {
            $historiqueMessageModel = new HistoriqueMessageModel();
            foreach ($checkBoxSupprimer as $currIdMessage) {
                $historiqueMessageModel->where(["IDMESSAGE" => $currIdMessage])->delete();
                $messageModel->delete(["IDMESSAGE" => $currIdMessage]);
            }


            return Utilitaires::success("messages supprimes avec succes");
        } catch (\Exception $err) {
            return Utilitaires::error('Erreur lors de la suppression');
        }
    }


    public function lister(): string
    {
        $messageModel = new MessageModel();
        $data = $messageModel->findAll();

        return view('listeMessage', ['data' => $data]);
    }

    public function visualiserActifs(): string
    {
        $messageModel = new MessageModel();
        $data = $messageModel->where([
            'ENLIGNE' => true,
        ])->findAll();
        return view('/message/visualiserMessages', ['data' => $data]);
    }

    public function creerForm()
    {
        return view('message/messageForm', ['action' => '/creer-message']);
    }

    private function upload(): array
    {
        if ($this->isValidBackgroundImage()) { // verifie la requete http
            return $this->uploadBackgroundImage();
        } else {
            return ['isOk' => false, 'error' => 'Le fichier n \'est pas une image'];
        }
    }

    private function isValidBackgroundImage(): bool
    {
        $validationRule = [
            'imageBackground' => [
                'label' => 'Image File',
                'rules' => [
                    'uploaded[imageBackground]',
                    'is_image[imageBackground]',
                    'mime_in[imageBackground,image/jpg,image/jpeg,image/gif,image/png,image/webp]',
                    // 'max_size[imageBackground,100]',
                    // 'max_dims[imageBackground,1024,768]',
                ],
            ],
        ];
        return $this->validate($validationRule);
    }
    private function uploadBackgroundImage()
    {
        $img = $this->request->getFile('imageBackground');

        if (!$img->hasMoved()) {
            $relativePath = $img->store();

            return ['isOk' => true, 'path' => $relativePath];
        } else {
            return ['isOk' => false, 'error' => 'Le fichier a changé de place'];
        }
    }

    public function showImage($pathFolder, $pathFile)
    {
        $path = $pathFolder . '/' . $pathFile;
        $filepath = WRITEPATH . 'uploads/' . $path;

        $mime = mime_content_type($filepath);
        header('Content-Length: ' . filesize($filepath));
        header("Content-Type: $mime");
        header('Content-Disposition: inline; filename="' . $filepath . '";');
        readfile($filepath);
        exit();

    }

    public function historique($idHistoriquemessage)
    {
        $historiquemessage = $this->historiqueMessageModel->where(['IDMESSAGE' => $idHistoriquemessage])->findAll();
        if (!$historiquemessage) {
            Utilitaires::error('Ce message n\'existe pas');
        }
        return view('/message/historiqueMessage', ['data' => $historiquemessage]);
    }


}
>>>>>>> Stashed changes
