<<<<<<< Updated upstream
<?php
use App\Controllers\Message;

/**
 * @var CodeIgniter\View\View $this
 */
?>

<?= $this->extend('default') ?>

<?= $this->section('titre') ?>

<?= $this->endSection() ?>

<?= $this->section('main') ?>
<div class="container">
    <h1>Liste des messages</h1>
    <a class="nav-link active" aria-current="page" target="_blank" href="/visualiser-messages-actifs">Visualiser</a>
    <a class="nav-link active" aria-current="page" href="/creer-message">Creer message</a>
    <form action="/supprimer-message" method="POST" class="table-responsive">
        <table class="table table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>Titre</th>
                    <th>Contenu</th>
                    <th>Image</th>
                    <th>taille titre</th>
                    <th>taille message</th>
                    <th>police titre</th>
                    <th>police message</th>
                    <th>Statut</th>
                    <th>Modifier</th>
                    <th>Supprimer</th>
                    <th>Historique</th>
                </tr>
            </thead>
            <tbody>
                <?php foreach ($data as $message): ?>
                    <tr>
                        <td>
                            <?= $message['TITREMESSAGE']; ?>
                        </td>
                        <td>
                            <?= $message['CONTENUMESSAGE']; ?>
                        </td>
                        <td>
                            <a href='<?= base_url('images/' . $message['IMAGEMESSAGE']) ?>' target='_blank'>Voir
                                l'image</a>
                        </td>

                        <td>
                            <?= $message['TAILLEPOLICETITRE'] ?>
                        </td>
                        <td>
                            <?= $message['TAILLEPOLICECONTENU'] ?>
                        </td>
                        <td>
                            <?= $message['TYPEPOLICETITRE'] ?>
                        </td>
                        <td>
                            <?= $message['TYPEPOLICECONTENU'] ?>
                        </td>

                        <td>
                            <?php if ($message['ENLIGNE'] == 0): ?>
                                Hors ligne
                            <?php else: ?>
                                En ligne
                            <?php endif; ?>
                        </td>
                        <td>
                            <a href="<?= site_url('/modifier-message/' . $message['IDMESSAGE']) ?>"> modifier </a>
                        </td>
                        <td>
                            <label for="inputSupprimer<?= $message['IDMESSAGE'] ?>">
                                <input type="checkbox" name="checkboxSupprimer[]" value="<?= $message['IDMESSAGE']; ?>"
                                    id="inputSupprimer<?= $message['IDMESSAGE'] ?>">
                                Supprimer
                            </label>
                        </td>
                        <td>
                            <a href="<?= site_url('/historique-message/' . $message['IDMESSAGE']) ?>"> historique </a>
                        </td>
                    </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
        <input type="submit" name="submit" value="Supprimer" />
    </form>
</div>
=======
<?php
use App\Controllers\Message;

/**
 * @var CodeIgniter\View\View $this
 */
?>

<?= $this->extend('default') ?>

<?= $this->section('titre') ?>

<?= $this->endSection() ?>

<?= $this->section('main') ?>
<div class="container">
    <h1>Liste des messages</h1>
    <a class="nav-link active" aria-current="page" target="_blank" href="/visualiser-messages-actifs">Visualiser</a>
    <a class="nav-link active" aria-current="page" href="/creer-message">Creer message</a>
    <form action="/supprimer-message" method="POST" class="table-responsive">
        <table class="table table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>Titre</th>
                    <th>Contenu</th>
                    <th>Image</th>
                    <th>taille titre</th>
                    <th>taille message</th>
                    <th>police titre</th>
                    <th>police message</th>
                    <th>Statut</th>
                    <th>Modifier</th>
                    <th>Supprimer</th>
                    <th>Historique</th>
                    
                </tr>
            </thead>
            <tbody>
                <?php foreach ($data as $message): ?>
                    <tr>
                        <td>
                            <?= $message['TITREMESSAGE']; ?>
                        </td>
                        <td>
                            <?= $message['CONTENUMESSAGE']; ?>
                        </td>
                        <td>
                            <a href='<?= base_url('images/' . $message['IMAGEMESSAGE']) ?>' target='_blank'>Voir
                                l'image</a>
                        </td>

                        <td>
                            <?= $message['TAILLEPOLICETITRE'] ?>
                        </td>
                        <td>
                            <?= $message['TAILLEPOLICECONTENU'] ?>
                        </td>
                        <td>
                            <?= $message['TYPEPOLICETITRE'] ?>
                        </td>
                        <td>
                            <?= $message['TYPEPOLICECONTENU'] ?>
                        </td>

                        <td>
                            <?php if ($message['ENLIGNE'] == 0): ?>
                                Hors ligne
                            <?php else: ?>
                                En ligne
                            <?php endif; ?>
                        </td>
                        <td>
                            <a href="<?= site_url('/modifier-message/' . $message['IDMESSAGE']) ?>"> modifier </a>
                        </td>
                        <td>
                            <label for="inputSupprimer<?= $message['IDMESSAGE'] ?>">
                                <input type="checkbox" name="checkboxSupprimer[]" value="<?= $message['IDMESSAGE']; ?>"
                                    id="inputSupprimer<?= $message['IDMESSAGE'] ?>">
                                Supprimer
                            </label>
                        </td>
                        <td>
                            <a href="<?= site_url('/historique-message/' . $message['IDMESSAGE']) ?>"> historique </a>
                        </td>

                    </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
        <input type="submit" name="submit" value="Supprimer" />
    </form>
</div>
>>>>>>> Stashed changes
<?= $this->endSection() ?>