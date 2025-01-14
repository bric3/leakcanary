package shark.internal

import shark.GcRoot
import shark.LeakReference
import shark.LibraryLeakReferenceMatcher

internal sealed class ReferencePathNode {
  abstract val objectId: Long

  class RootNode(
    val gcRoot: GcRoot,
    override val objectId: Long
  ) : ReferencePathNode()

  sealed class ChildNode : ReferencePathNode() {

    abstract val parent: ReferencePathNode

    /**
     * The reference from the parent to this node
     */
    abstract val referenceFromParent: LeakReference

    class LibraryLeakNode(
      override val objectId: Long,
      override val parent: ReferencePathNode,
      override val referenceFromParent: LeakReference,
      val matcher: LibraryLeakReferenceMatcher
    ) : ChildNode()

    class NormalNode(
      override val objectId: Long,
      override val parent: ReferencePathNode,
      override val referenceFromParent: LeakReference
    ) : ChildNode()
  }

}