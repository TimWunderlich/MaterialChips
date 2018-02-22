# MaterialChips

An implementation of Chips as specified by [Google’s Material Design](https://material.io/guidelines/components/chips.html).

![MaterialChips preview](https://raw.githubusercontent.com/TiMWunderlich/MaterialChips/master/docs/tags.png)

Realizes layout as a RecyclerView using [ChipsLayoutManager](https://github.com/BelooS/ChipsLayoutManager) and allows to easily append and delete individual items programmatically.

## Gradle
    dependencies {
        ...
        implementation 'org.tiwu:materialchips:0.1.0'
    }
    
    
## Usage

### Layout integration

    <org.tiwu.materialchips.ChipsView
        android:id="@+id/chips_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
        
        
### Trigger deletability

Chips are not deletable by default and consequently lack a delete button. If you want them to be deletable, set the ChipView’s ```deletable``` property to ```true```:

    chips_view.isDeletable = true
    
        
### Adding items

MaterialChips uses an object’s ```toString()``` method to determine the label of a Chip:

    val item = MyClass("label")
    chips_view.addItem(item)
​

### Thumbnails
​
Using [CircleImageView](https://github.com/hdodenhof/CircleImageView), objects implementing the [```ImageProvider```](https://github.com/TimWunderlich/MaterialChips/blob/master/materialchips/src/main/java/org/tiwu/materialchips/ImageProvider.java) interface can be shown with a thumbnail:

    class Cat(val label: String, val bitmap: Bitmap) : ImageProvider {
        override fun toString(): String = label
        override fun getBitmap(): Bitmap? = bitmap
    }
    
    chips_view.addItem(Cat("Cat without thumbnail", null))    
    chips_view.addItem(Cat("Cat with thumbnail", bitmap))
​
![Thumbnails](https://raw.githubusercontent.com/TiMWunderlich/MaterialChips/master/docs/cat.png)
    
    
### Finding items

MaterialChips wraps around ```List.indexOf()``` to return the index of a given item:

    val position: Int = chips_view.indexOf(item)
    
    
### Removing items

Specify the position of an item to remove it from the view:

    chips_view.removeItem(position)


### Handling onClick events

Implement ChipsView.OnClickListener:

    chips_view.onClickListener = object : ChipsView.OnClickListener() {
        override fun onDelete(item: Any, position: Int) {
            Toast.makeText(context, alertItemClicked, Toast.LENGTH_SHORT).show()
        }
    }


### Handling onDelete events

Implement ChipsView.onDeleteListener:

    chips_view.onDeleteListener = object : ChipsView.OnDeleteListener() {
        override fun onDelete(item: Any, position: Int): Boolean {
            Toast.makeText(context, alertItemDeleted, Toast.LENGTH_SHORT).show()
            return true
        }
    }

```onDelete()``` has to return ```true``` if the item should be removed from the view and ```false``` if it should remain.
