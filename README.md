# MaterialChips

An implementation of Chips as specified by [Google’s Material Design](https://material.io/guidelines/components/chips.html).

![MaterialChips preview](https://raw.githubusercontent.com/TiMWunderlich/MaterialChips/master/docs/tags.png)

## Gradle
    dependencies {
        ...
        compile 'org.tiwu:materialchips:0.1.0'
    }

## Features

### OnClickListener

    chips_view.onClickListener = object : ChipsView.OnClickListener() {
        override fun onDelete(item: Any, position: Int) {
            Toast.makeText(context, alertItemClicked, Toast.LENGTH_SHORT).show()
        }
    }

### OnDeleteListener

    chips_view.onDeleteListener = object : ChipsView.OnDeleteListener() {
        override fun onDelete(item: Any, position: Int): Boolean {
            Toast.makeText(context, alertItemDeleted, Toast.LENGTH_SHORT).show()
            return true
        }
    }

### Thumbnails

Using [CircleImageView](https://github.com/hdodenhof/CircleImageView), objects implementing the [```ImageProvider```](https://github.com/TimWunderlich/MaterialChips/blob/master/materialchips/src/main/java/org/tiwu/materialchips/ImageProvider.java) interface can be shown with a thumbnail:

![Thumbnails](https://raw.githubusercontent.com/TiMWunderlich/MaterialChips/master/docs/cat.png)
