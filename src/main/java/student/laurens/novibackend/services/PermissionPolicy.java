package student.laurens.novibackend.services;

public enum PermissionPolicy {
    ALLOW,
    ALLOW_PARENT_OWNED,
    ALLOW_PARENT_OR_CHILD_OWNED,
    ALLOW_CHILD_OWNED,
    DENY,
}
