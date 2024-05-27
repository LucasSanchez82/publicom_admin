<h1>salut</h1>
<?php
/**
 * @var CodeIgniter\View\View $this
 */
?>
<?= $this->extend('default') ?>

<?= $this->section('titre') ?>

<?= $this->endSection() ?>

<?= $this->section('main') ?>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
        <form action="/modifier-message" method="post" enctype="multipart/form-data">
                <input style="display: none;" name="idMessage" type="text" value="<?= empty($IDMESSAGE) ? '' : $IDMESSAGE ?>">
                <div class="mb-3">
                    <label for="titre" class="form-label">Titre</label>
                    <input type="text" value="<?= empty($TITREMESSAGE) ? '' : $TITREMESSAGE ?>" name="titre" id="titre" class="form-control"
                        required>
                </div>
                <div class="mb-3">
                    <label for="message" class="form-label">Message</label>;
                    <input type="textarea" name="message" id="message" class="form-control" style="height: 200px"
                        required value="<?= empty($CONTENUMESSAGE) ? '' : $CONTENUMESSAGE ?>">
                </div>
                <div>
                    <label for="policeTitre" class="form-label">Police titre</label>
                    <select id="policeTitre" name="policeTitre">
                        <option <?= empty($TYPEPOLICETITRE) || $TYPEPOLICETITRE === 'Franklin Gothic Medium' ? 'selected' : '' ?> value="Franklin Gothic Medium">Franklin Gothic Medium</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Arial Narrow' ? 'selected' : '' ?> value="Arial Narrow">Arial Narrow</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Arial' ? 'selected' : '' ?> value="Arial">Arial</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Courier New' ? 'selected' : '' ?> value="Courier New">Courier New</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Courier' ? 'selected' : '' ?> value="Courier">Courier</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Gill Sans' ? 'selected' : '' ?> value="Gill Sans">Gill Sans</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Gill Sans MT' ? 'selected' : '' ?> value="Gill Sans MT">Gill Sans MT</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Calibri' ? 'selected' : '' ?> value="Calibri">Calibri</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Trebuchet MS' ? 'selected' : '' ?> value="Trebuchet MS">Trebuchet MS</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Lucida Sans' ? 'selected' : '' ?> value="Lucida Sans">Lucida Sans</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Lucida Grande' ? 'selected' : '' ?> value="Lucida Grande">Lucida Grande</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Lucida Sans Unicode' ? 'selected' : '' ?> value="Lucida Sans Unicode">Lucida Sans Unicode</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Times New Roman' ? 'selected' : '' ?> value="Times New Roman">Times New Roman</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Times' ? 'selected' : '' ?> value="Times">Times</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Impact' ? 'selected' : '' ?> value="Impact">Impact</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Haettenschweiler' ? 'selected' : '' ?> value="Haettenschweiler">Haettenschweiler</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Arial Narrow Bold' ? 'selected' : '' ?> value="Arial Narrow Bold">Arial Narrow Bold</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Verdana' ? 'selected' : '' ?> value="Verdana">Verdana</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Geneva' ? 'selected' : '' ?> value="Geneva">Geneva</option>
                        <option <?= !empty($TYPEPOLICETITRE) && $TYPEPOLICETITRE === 'Tahoma' ? 'selected' : '' ?> value="Tahoma">Tahoma</option>
                    </select>

                </div>
                <div>
                    <label for="policeContenu" class="form-label">Police message</label>
                    <select id="policeContenu" name="policeContenu">
                        <option <?= empty($TYPEPOLICECONTENU) || $TYPEPOLICECONTENU === 'Franklin Gothic Medium' ? 'selected' : '' ?> value="Franklin Gothic Medium">Franklin Gothic Medium</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Arial Narrow' ? 'selected' : '' ?> value="Arial Narrow">Arial Narrow</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Arial' ? 'selected' : '' ?> value="Arial">Arial</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Courier New' ? 'selected' : '' ?> value="Courier New">Courier New</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Courier' ? 'selected' : '' ?> value="Courier">Courier</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Gill Sans' ? 'selected' : '' ?> value="Gill Sans">Gill Sans</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Gill Sans MT' ? 'selected' : '' ?> value="Gill Sans MT">Gill Sans MT</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Calibri' ? 'selected' : '' ?> value="Calibri">Calibri</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Trebuchet MS' ? 'selected' : '' ?> value="Trebuchet MS">Trebuchet MS</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Lucida Sans' ? 'selected' : '' ?> value="Lucida Sans">Lucida Sans</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Lucida Grande' ? 'selected' : '' ?> value="Lucida Grande">Lucida Grande</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Lucida Sans Unicode' ? 'selected' : '' ?> value="Lucida Sans Unicode">Lucida Sans Unicode</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Times New Roman' ? 'selected' : '' ?> value="Times New Roman">Times New Roman</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Times' ? 'selected' : '' ?> value="Times">Times</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Impact' ? 'selected' : '' ?> value="Impact">Impact</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Haettenschweiler' ? 'selected' : '' ?> value="Haettenschweiler">Haettenschweiler</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Arial Narrow Bold' ? 'selected' : '' ?> value="Arial Narrow Bold">Arial Narrow Bold</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Verdana' ? 'selected' : '' ?> value="Verdana">Verdana</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Geneva' ? 'selected' : '' ?> value="Geneva">Geneva</option>
                        <option <?= !empty($TYPEPOLICECONTENU) && $TYPEPOLICECONTENU === 'Tahoma' ? 'selected' : '' ?> value="Tahoma">Tahoma</option>
                    </select>
                </div>

                <div>
                    <label for="alignementMessage" class="form-label">Alignement message</label>
                    <select id="alignementMessage" name="alignementMessage">
                        <option <?= empty($ALIGNEMENTMESSAGE) || $ALIGNEMENTMESSAGE === 'justify' ? 'selected' : '' ?> value="justify">Justifier</option>
                        <option <?= !empty($ALIGNEMENTMESSAGE) && $ALIGNEMENTMESSAGE === 'center' ? 'selected' : '' ?> value="center">centrer</option>
                        <option <?= !empty($ALIGNEMENTMESSAGE) && $ALIGNEMENTMESSAGE === 'right' ? 'selected' : '' ?> value="right">droite</option>
                        <option <?= !empty($ALIGNEMENTMESSAGE) && $ALIGNEMENTMESSAGE === 'left' ? 'selected' : '' ?> value="left">gauche</option>
                    </select>
                </div>

                <div>
                    <label for="policeTailleTitre" class="form-label">Taille titre</label>
                    <input type="number" name="policeTailleTitre" id="policeTailleTitre" class="form-control" value="<?= empty($TAILLEPOLICETITRE) ? '16' : $TAILLEPOLICETITRE ?>"
                        required>
                </div>
                <div>
                    <label for="policeTailleContenu" class="form-label">Taille message</label>
                    <input type="number" name="policeTailleContenu" id="policeTailleContenu" class="form-control"
                        value="<?= empty($TAILLEPOLICECONTENU) ? '12' : $TAILLEPOLICECONTENU ?>" required>
                </div>
                <div class="mb-3">
                    <label for="formFile" class="form-label">Default file input example</label>
                    <input class="form-control" accept="image/*" name="imageBackground" type="file" id="formFile">
                </div>

                <div class="form-check form-switch">
                    <input class="form-check-input" name="enLigne" type="checkbox" role="switch" aria-checked="<?= !empty($ENLIGNE) && $ENLIGNE ? 'true' : 'false' ?>"
                        id="flexSwitchCheckDefault" <?= !empty($ENLIGNE) && $ENLIGNE ? 'checked' : '' ?>>
                    <label class="form-check-label" for="flexSwitchCheckDefault">En ligne</label>
                </div>
                <input type="submit" value="<?= empty($IDMESSAGE) ? 'Ajouter message' : 'Modifier message' ?>" class="btn btn-primary" />
            </form>
        </div>
    </div>
</div>

<?= $this->endSection() ?>