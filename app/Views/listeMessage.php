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
        <table class="table table-bordered" data-toggle="table" data-sortable="true" id="table">
            <thead class="thead-dark">
                <tr>
                    <th data-field="titre" data-sortable="true">Titre</th>
                    <th data-field="contenu" data-sortable="true">Contenu</th>
                    <th data-field="image">Image</th>
                    <th data-field="taille-titre" data-sortable="true">taille titre</th>
                    <th data-field="taille-message" data-sortable="true">taille message</th>
                    <th data-field="police-titre" data-sortable="true">police titre</th>
                    <th data-field="police-message" data-sortable="true">police message</th>
                    <th data-field="statut" data-sortable="true">Statut</th>
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

    <script>
      document.addEventListener('DOMContentLoaded', function() {
       var tableElement = document.getElementById('table');
       if (tableElement) {
           var bootstrapTableInstance = new bootstrapTable.BootstrapTable(tableElement, {
               // options ici
           });
       } else {
           console.error('Table element not found!');
       }
   });
   </script>
</div>
<?= $this->endSection() ?>
